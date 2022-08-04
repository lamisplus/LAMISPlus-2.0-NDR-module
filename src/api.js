//export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNjU5NjUzNDI4fQ.bwrgPSSM_0BTWuq3j4_l8NXNFrdATq8Yf0zj5uxQQIHq5bTdrJwI5HpKv41wLkATDN6t6B9r0aqmrcjtgmfW4w';
export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'
//export const url =  'http://localhost:8282/api/v1/';