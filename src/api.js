//export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNjU4ODQ5NTIwfQ.4Qs_ihDvgopZfNiXxYaHfNVUnxV1TiMGZgqaduphg2APTsO3QnTM_-sDpx_flBrA3pA-pSvUoynyzY-D12uKrQ';
export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/'
//export const url =  'http://localhost:8282/api/v1/';