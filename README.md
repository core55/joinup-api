## Intro

## Installation 

## API endpoints


PATCH?
PUT?


Request |Address                         |Function
:-------|:-------------------------------|:---------------------------------------------------
`GET`   |`api/meetups`                   |return all `meetup` objects
`GET`   |`api/users`                     |return all `user` objects
`POST`  |`api/meetups`                   |add `meetup` object to DB
`POST`  |`api/users`                     |add `user` object to DB
`GET`   |`api/meetups/\<hash>`           |return specific `meetup` object
`GET`   |`api/users/\<id>`               |return specific `user` object
`DELETE`|`api/meetups/\<hash>`           |delete specific `meetup` object
`DELETE`|`api/users/\<id>`               |delete specific `user` object
`POST`  |`api/meetups/\<hash>/users/save`|save `user` object and attach to specified `meetup`
`GET`   |`api/meetups/\<hash>/users`     |return all `users` connected to a `meetup`



how do we accomplish something?

### Fetch all meetups
`GET `

arguments and return values

``json

```



GET:
api/meetups
api/users

return the objects

POST:
api/meetups
api/users

add user/meetup to DB
