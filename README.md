# dry-cherry API Spec

## Intro

RESTful API for handling users and meetups in the ***JoinMe*** App.

Team website: [Core 55](https://core55.github.io/)

## Installation 

Clone with SSH: `git clone git@gits-15.sys.kth.se:core55/dry-cherry.git`

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

### Meetup

```json
{
    "id": 1,
    "createdAt": "2017.04.26.15.05.43",
    "updatedAt": "2017.04.26.15.05.43",
    "centerLongitude": 18.071716,
    "centerLatitude": 59.32683,
    "zoomLevel": 13,
    "hash": "fe2d29ad881d47b18eea2ac44b026a80",
    "pinLongitude": 13.079516,
    "pinLatitude": 24.547516,
    "name": "Evening at theater"
}
```

### Location
```json
{
    "id": 1,
    "createdAt": "2017.04.26.14.59.15",
    "updatedAt": "2017.04.26.14.59.15",
    "longitude": 18.072311,
    "latitude": 59.316486
}
```

## Summary table of HTTP Methods for RESTful Services

HTTP Verb|CRUD          |Address                        |Function                                           |Permission
:--------|--------------|:------------------------------|:-------------------------------------------------:|:---------------:
GET      |Read          |`api/meetups`                  |return all `meetup` objects                        |ADMIN
POST     |Create        |`api/meetups`                  |add `meetup` object to DB                          |ALL
GET      |Read          |`api/meetups/<hash>`           |return specific `meetup` object                    |ALL
PUT      |Update/Replace|`api/meetups/<hash>`           |substitute a `meetup` with a new `meetup` object   |ASSOCIATED USERS
PATCH    |Update/Modify |`api/meetups/<hash>`           |modify selected values of a `meetup` object        |ASSOCIATED USERS
DELETE   |Delete        |`api/meetups/<hash>`           |delete specific `meetup` object                    |CREATOR
POST     |Create        |`api/meetups/<hash>/users/save`|save `user` object and attach to specified `meetup`|ALL
GET      |Read          |`api/meetups/<hash>/users`     |return all `users` connected to a `meetup`         |ASSOCIATED USERS
GET      |Read          |`api/users`                    |return all `user` objects                          |ADMIN
POST     |Create        |`api/users`                    |add `user` object to DB                            |ADMIN
GET      |Read          |`api/users/<id>`               |return specific `user` object                      |OWN USER
PUT      |Update/Replace|`api/users/<id>`               |substitute a `user` with a new `user` object       |OWN USER
PATCH    |Update/Modify |`api/users/<id>`               |modify selected values of a `user` object          |OWN USER
DELETE   |Delete        |`api/users/<id>`               |delete specific `user` object                      |OWN USER
GET      |Read          |`api/users/<id>/locations`     |return the ten last locations of a `user`          |OWN USER


## API endpoints

### Create a meetup

`POST /api/meetups`

Store a new meetup in the database and return it.

Example request body:
```json
{
    "centerLongitude": 18.071716,
    "centerLatitude": 59.32683,
    "zoomLevel": 13
}
```
**Required fields:** `centerLongitude`, `centerLatitude`, `zoomLevel`<br>
**Optional fields:** `pinLongitude`, `pinLatitude`, `name`

### Find all meetups

`GET /api/meetups`

Return a list of all the meetups stored in the database.

### Find a meetup

`GET /api/meetups/<hash>`

Return the meetup specified by the value by the `<hash>`

### Update a meetup 

`PUT api/meetups/<hash>`

Substitute the meetup specified by the `<hash>` value with a full new provided meetup. 
The provided meetup should have all the fields of the meetup that is going to replace.

### Update values of a meetup 

`PATCH api/meetups/<hash>`

Update only the fields corresponding to the provided values of the objects specified by the `<hash>`
