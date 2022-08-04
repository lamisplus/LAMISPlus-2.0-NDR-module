import React, {useState, useEffect} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import * as api from "./../../../api";
import axios from "axios";
import { CardBody, Card} from 'reactstrap';
import MaterialTable from 'material-table';
import Tooltip from '@material-ui/core/Tooltip';
import IconButton from '@material-ui/core/IconButton';
import CloudDownloadIcon from '@material-ui/icons/CloudDownload';
import { Link } from "react-router-dom";
import { forwardRef } from 'react';
import { FiUploadCloud } from "react-icons/fi";
import {  Modal, ModalBody, ModalHeader } from 'reactstrap';

import AddBox from '@material-ui/icons/AddBox';
import ArrowUpward from '@material-ui/icons/ArrowUpward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import Button from '@material-ui/core/Button';
const tableIcons = {
Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
SortArrow: forwardRef((props, ref) => <ArrowUpward {...props} ref={ref} />),
ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
};

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


export default function DownloadNdr() {
  const classes = useStyles();  
  const [generatedNdrListed, setGeneratedNdrList] = useState( [])
  const [loading, setLoading] = useState('')
  const [modal, setModal] = useState(false);
  const toggle = () => setModal(!modal);

  useEffect(() => {
    generatedNdrList()
  }, []);

  //Load NDR WEB in the modal\
  const loadNdrWeb = () =>{
    toggle()
  }

 ///GET LIST OF FACILITIES
 async function generatedNdrList() {
  axios
      .get(`${api.url}ndr/files`,
        { headers: {"Authorization" : `Bearer ${api.token}`} }
        )
      .then((response) => {
        console.log(response.data)
        setGeneratedNdrList(response.data);
        console.log(response.data);
       
         })
      .catch((error) => {
      });

}





  return (
    <div >      
          
                <Button
                    variant="contained"
                    color="primary"
                    className=" float-right"
                    startIcon={<FiUploadCloud size="10"/>}
                    style={{backgroundColor:'#014d88'}}
                    href="https://ndr.phis3project.org.ng/Identity/Account/Login?ReturnUrl=%2F"
                    //onClick={loadNdrWeb}
                    target="_blank"
                >
                    <span >Upload to NDR</span>
                </Button>
           
                <br/><br/>      
            <MaterialTable
            icons={tableIcons}
            title="List of Filies Generated"
            
            columns={[
                { title: "Facility Name", field: "name", filtering: false },
                {
                  title: "Number of Files Generated",
                  field: "files",
                  filtering: false
                },
                { title: "Date Last Generated", field: "date", type: "date" , filtering: false},          

                {
                  title: "Action",
                  field: "actions",
                  filtering: false,
                },
            ]}
            isLoading={loading}
            data={generatedNdrListed.map((row) => ({
                name:row.facility,
                files:row.files,
                date:row.lastModified,             
                actions: 
                //to={row.url+"/"+row.name} 
                        <Link to={"download/"+row.fileName}  target="_blank" download>
                          <Tooltip title="Download">
                              <IconButton aria-label="Download" >
                                  <CloudDownloadIcon color="primary"/>
                              </IconButton>
                          </Tooltip>
                          </Link>

            }))}
            options={{
                
                pageSizeOptions: [5,10,50,100,150,200],
                headerStyle: {
                backgroundColor: "#014d88",
                color: "#fff",
                margin: "auto"
                },
                filtering: true,
                searchFieldStyle: {
                    width : '300%',
                    margingLeft: '250px',
                },
                exportButton: false,
                searchFieldAlignment: 'left',          
            }}

        />      
    
      <Modal isOpen={modal} toggle={toggle} backdrop={false} fade={true} size="xl" style={{marginTop:"50px"}}>
          <ModalHeader toggle={toggle}></ModalHeader>
          <ModalBody>
          <iframe  style={{width:"100%", height:"100%", border:"none", margin:0, padding:0}} src="https://ndr.phis3project.org.ng/Identity/Account/Login?ReturnUrl=%2F" ></iframe> 
          <embed src="https://ndr.phis3project.org.ng/"
            width="100%"
            height="1000"
            onerror="alert('URL invalid !!');"
          />
          </ModalBody>
          
        </Modal> 
  </div>
    
  );
  
}
