# dry-cherry API Spec

## JSON Objects returned by API

### User

```json
{
         "id": 1,
         "createdAt": "2017.04.26.15.05.43",
         "updatedAt": "2017.04.26.15.05.43",
         "nickname": "James",
         "lastLongitude": 18.072311,
         "lastLatitude": 59.316486,
         "username": "d9e313a2ee584b5b9ed55fcc3c356afe"
       }
```

### Meetups

```json
{
  "id": 1,
  "createdAt": "2017.04.26.15.05.43",
  "updatedAt": "2017.04.26.15.05.43",
  "centerLongitude": 18.071716,
  "centerLatitude": 59.32683,
  "zoomLevel": 13,
  "hash": "fe2d29ad881d47b18eea2ac44b026a80",
  "pinLongitude": null,
  "pinLatitude": null,
  "name": null
}
```

## API endpoints

## Summary table of HTTP Methods for RESTful Services

HTTP Verb|CRUD          |Address                        |Function
:--------|--------------|:------------------------------|:---------------------------------------------------
GET      |Read          |`api/users`                    |return all `user` objects
POST     |Create        |`api/users`                    |add `user` object to DB
GET      |Read          |`api/users/<id>`               |return specific `user` object
DELETE   |Delete        |`api/users/<id>`               |delete specific `user` object
GET      |Read          |`api/meetups`                  |return all `meetup` objects
POST     |Create        |`api/meetups`                  |add `meetup` object to DB
GET      |Read          |`api/meetups/<hash>`           |return specific `meetup` object
DELETE   |Delete        |`api/meetups/<hash>`           |delete specific `meetup` object
POST     |Create        |`api/meetups/<hash>/users/save`|save `user` object and attach to specified `meetup`
GET      |Read          |`api/meetups/<hash>/users`     |return all `users` connected to a `meetup`
PUT      |Update/Replace|**                             |**
PATCH    |Update/Modify |**                             |**

** yet to be implemented