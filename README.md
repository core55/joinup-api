## Summary table of HTTP Methods for RESTful Services

HTTP Verb|CRUD          |Address                        |Function
:--------|--------------|:------------------------------|:---------------------------------------------------
GET      |Read          |`api/users`                    |return all `user` objects
POST     |Create        |`api/users`                    |add `user` object to DB
GET      |Read          |`api/users/\<id>`              |return specific `user` object
DELETE   |Delete        |`api/users/\<id>`              |delete specific `user` object
GET      |Read          |`api/meetups`                  |return all `meetup` objects
POST     |Create        |`api/meetups`                  |add `meetup` object to DB
GET      |Read          |`api/meetups/<hash>`           |return specific `meetup` object
DELETE   |Delete        |`api/meetups/<hash>`           |delete specific `meetup` object
POST     |Create        |`api/meetups/<hash>/users/save`|save `user` object and attach to specified `meetup`
GET      |Read          |`api/meetups/<hash>/users`     |return all `users` connected to a `meetup`
PUT      |Update/Replace|**                             |**
PATCH    |Update/Modify |**                             |**

** yet to be implemented