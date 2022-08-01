import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Checkbox from '@material-ui/core/Checkbox';
import axios from "axios";
import * as api from "./../../../api";
import { CardBody, Card} from 'reactstrap';
import { Alert, AlertTitle } from '@material-ui/lab';
import {GiFiles} from 'react-icons/gi'; 
import Button from "@material-ui/core/Button";
import AccountBalanceIcon from '@material-ui/icons/AccountBalance';
import {  Modal, ModalBody } from 'reactstrap';
import { useHistory } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "react-widgets/dist/css/react-widgets.css";

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
    '& > * + *': {
      marginTop: theme.spacing(2),
    },
  },
}));


export default function GenerateNdr(props) {
  let history = useHistory();
  const classes = useStyles();
  const [facilities, setFacilities] = useState( [])
  const [processing, setProcessing] =  useState(false)
  const [facilitiesApi, setfacilitiesApi] = useState({facilities: []})
  
  const [checked, setChecked] = React.useState([]);  
  const [user, setUser] = useState(null);
  const [modal, setModal] = useState(false);

  const toggle = () => setModal(!modal);

  useEffect(() => {
    fetchMe()
  }, []);

 ///GET LIST OF FACILITIES
  async function fetchMe() {

    axios
        .get(`${api.url}account`,
        { headers: {"Authorization" : `Bearer ${api.token}`} }
          )
        .then((response) => {
          setUser(response.data);
          setFacilities(response.data.applicationUserOrganisationUnits);
           })
        .catch((error) => {

        });
  
}

//GET LIST OF NDR GENERATED



  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];
    
    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };



 const  generateAction = () => {
   setProcessing(true)
   toggle();
   let FacilityIDArray = [];
  //LOOPING THROUGH THE FACILITIES OBJECT ARRAY TO FORM THE NEW OBJECT 
   checked.forEach(function(value) {
    delete value['id'];
    delete value['applicationUserId'];
    delete value['archived'];
    FacilityIDArray.push(value);
       
   });
   facilitiesApi['facilities'] = FacilityIDArray;
   //SENDING A POST REQUEST 
   axios.post(`${api.url}ndr/generate`, facilitiesApi,
              { headers: {"Authorization" : `Bearer ${api.token}`} }
              )
            .then(response => {
              toast.success(" Generating NDR Successful!");
              props.history.push("/generate", { state: 'download'});
            })
            .catch(error => {
              setModal(false)
              setProcessing(false) // set the generate button true
              //toast.error(" Something went wrong!");
              if(error.response && error.response.data){
                let errorMessage = error.response.data.apierror && error.response.data.apierror.message!=="" ? error.response.data.apierror.message :  "Something went wrong, please try again";
                toast.error(errorMessage);
              }
              else{
                toast.error("Something went wrong. Please try again...");
              }
            });
        }


  return (
    <div > 
    <ToastContainer autoClose={3000} hideProgressBar />    
      <Card>
      
        <CardBody> 
   
            
          {checked.length >= 1 ? <>
            <Button
              color="primary"
              variant="contained"
              className=" float-right mr-1"
              size="large"            
              hidden={processing}
              onClick= {() => generateAction()}
            >
              {<GiFiles />} &nbsp;&nbsp;
              <span style={{textTransform: 'capitalize'}}> Generate Messages</span>
              
            </Button>
            </>
            :
            <>
            <Button
              color="primary"
              variant="contained"
              className=" float-right mr-1"
              size="large"
              disabled="true"
            >
              {<GiFiles />} &nbsp;&nbsp;
              <span style={{textTransform: 'capitalize'}}> Generate Messages </span>
              
            </Button>
            </>
        }

            <>
            <br/> <br/>
            <Alert severity="info">
            <AlertTitle>Info</AlertTitle>
              Please check the Facilities you want  
            </Alert>
            <br/>
          
          <List dense className={classes.root} >
                  
      <br/>

        {facilities.map((value) => {
          //console.log(value)
          const labelId = `checkbox-list-secondary-label-${value.id}`;
          return (
            <ListItem key={value.id} button>
              <ListItemAvatar>
                <AccountBalanceIcon />
              </ListItemAvatar>
              <ListItemText id={labelId} primary={`${value.organisationUnitName }`} />
              <ListItemSecondaryAction>
                <Checkbox
                  edge="end"
                  onChange={handleToggle(value)}
                  checked={checked.indexOf(value) !== -1}
                  inputProps={{ 'aria-labelledby': labelId }}
                />
              </ListItemSecondaryAction>
            </ListItem>
            
          );
          
        })}
        </List>
       
        </>
       
           
      </CardBody>
    </Card>
    <Modal isOpen={modal} toggle={toggle} backdrop={false} fade={true} style={{marginTop:"250px"}} >
        
        <ModalBody>
         <h2>Generating NDR Please wait...</h2>
        </ModalBody>
        
      </Modal> 
  </div>
    
  );
  
}