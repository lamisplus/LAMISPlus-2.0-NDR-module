//export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNjU5NjQ2MzY0fQ.rcoWTEMfRR9BG_xnxBNcpu2usme3AWQQtE7lo3I2ym2JxizfE7XS9z5qplg2C7U3W-__E2_dRqFVqYyby5o9hg ';
export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'
//export const url =  'http://localhost:8282/api/v1/';