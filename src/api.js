//export const  token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJndWVzdEBsYW1pc3BsdXMub3JnIiwiYXV0aCI6IlN1cGVyIEFkbWluIiwibmFtZSI6Ikd1ZXN0IEd1ZXN0IiwiZXhwIjoxNjYwMTgwODQ0fQ.Xq_D6kRJAtCKC71vMAeWWgvyWklh_TJs9gc8VyBE8bY45D5ZuWKit4T3OSeYLoJEgk8TTZggy-OXROrxrwKi0Q';
export const  token = (new URLSearchParams(window.location.search)).get("jwt")
export const url = '/api/v1/'
//export const url =  'http://localhost:8282/api/v1/';
