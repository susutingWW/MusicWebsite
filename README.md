# MusicWebsite
### System technology selection
The music management system was developed using the following technology stack:
**Front-end technologies:**
- Vue.js: a JavaScript framework for building user interfaces.
- Element UI: a set of component libraries based on Vue.js that provide rich UI components and styles.
- ECharts: a powerful data visualisation library for generating charts and graphical presentations.

**Back-end technologies:**
- Spring Boot: development framework for rapidly building Java applications.
- Spring MVC: Web framework based on the Spring Framework for handling HTTP requests.
- MyBatis: an excellent persistence framework for database operations and ORM mapping.
- MySQL: A commonly used relational database management system for data storage and retrieval.
- Redis: an in-memory database used to efficiently cache data and provide fast access.
- JWT (JSON Web Token): an open standard for authentication and authorisation used to generate and validate access tokens.

The front-end of the system uses Vue.js as the main framework in conjunction with the Element UI component library for user interface construction and interaction.The ECharts library is used to generate various charts and data visualisations.

The back-end is developed using Spring Boot framework for rapid development, using Spring MVC to handle HTTP requests and build APIs.MyBatis framework is used for database operations and object-relational mapping with MySQL database for data storage and retrieval.Redis is used for caching data to improve the performance and responsiveness of the system.

For authentication and authorisation, the system uses the JWT standard to generate and validate access tokens to ensure user security and rights management.
### Development environment
1. IntelliJ IDEA: 2022
2. SpringBoot: 3.0.3
3. Mybatis: 3.0.3
4. MySQL: 8.0.29
5. Redis: 3.0.503
6. Vue: 2.x
7. JWT: 4.4.0
8. Java: 1.9

### Functional module design
#### UI design
**front end:**
###### Pages Design
login page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/2cd120e1-2e77-47de-b994-849b6e5c157a)

The login page includes a username and password entry box and a jump to the registration page and login button.
registration page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/ca82c088-a2f2-4318-8bff-10218ee24442)

Registration includes a form with information about the user and OK and Cancel buttons.
home page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/f0b71a15-e811-4b2e-b2d1-97f2ec9bda9d)

The home page includes a rotating image and a list of popular songs and artists. Clicking on a popular song and artist will redirect you to the details page.
song list detail page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/5838bd07-cbf0-4ad6-8527-3bcccf6d195a)

The song list details page shows the details of the song list and the songs in the list, which can be played by clicking on them.
artist details page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/9c8e1f2e-f556-411f-b870-7d53aa46bfcf)

Artist details page shows the details of the artist and the songs posted by the artist, and you can play them by clicking on them.
song search page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/a3672ec4-aa8c-44a9-b561-30c2f00424d9)

Song search page shows the songs that contain the keywords entered in the search box, click it to jump to the corresponding song details page.
song list search page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/64767c85-1434-40ca-adff-8447356cac2c)

Song list search page displays song lists that contain the keywords entered in the search box, and clicking on them takes you to the corresponding song list details page.
song detail page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/b9aeaf54-a89d-4313-b922-8c9243b91cdd)

Song details page shows the current lyrics according to the playing progress.
modify avatar page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/a7faaed5-cfe2-421d-a6a7-a32d19abc3d1)

You can upload a user avatar. You can click and drag to upload a picture.
modify information page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/60f0355f-1211-4706-a29f-4d82ee3ed14c)

You can edit personal information and submit it.
###### Component Design
header navigation bar:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/b618cc33-f7dc-4d1f-8520-154877b96fa9)

The top navigation bar can be clicked to jump to different pages, display user avatar, click avatar to enter the settings page and exit login. Search box can search songs and song list.
bottom navigation bar:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/81471465-7d32-4594-96a8-3ca813d16b2c)

Bottom navigation bar shows the current song, you can play the previous and next song, click the picture to enter the song details page, you can drag the progress bar to adjust the progress of the song. You can adjust the music volume and download the song. You can display the playlist.
back to top button:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/11084740-eb9c-4e2c-996e-f74cd9c0b387)

Back to top button, click it to return to the top of the page.
Playlist:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/f7f18764-086e-464d-9a41-8ae51d9070aa)

Playlist, show the list of playing songs, click to switch songs.
**back end:**
###### Pages Design
login page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/bb293526-a163-4c4c-9309-73918b870891)

Administrator login screen, enter user name and password.
home page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/6a09a5b4-a2a8-4836-97cb-353075cd5b9d)

Home page of the system, which visually displays some information about the website including the total number of users, the gender ratio of users and the distribution of song types.
user management page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/4356fea5-af5b-4ca5-a645-1d433385f7fb)

User management interface, administrator can modify user's avatar and information, add and delete users, search for users. You can also delete users in batch.
singer management page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/202e004b-43ca-4a28-8ab6-d9d58220a37e)

Singer management interface, the administrator can modify the singer's avatar, information, add and delete singers, search singers. You can also delete singers in batch. Click Song Management to enter the song management interface of the singer.
song list management page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/82d785d6-32ea-491a-9889-6eb82ec491ab)

Song list management interface, administrator can change song list image and information, add delete song list, search song list. You can also delete songs in batch. Click Song Management to enter the song management interface of the song list.
singer song management page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/0e2cc763-11cc-4641-92cd-94b72df10eec)

The singer's song management interface can play and pause the song, also can update the song and manage the song information. You can add, delete, and change songs.
song information page:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/a945137a-77b6-493e-b0c7-52d6e6cf3199)

You can add, delete, and retrieve songs in the song list on the song management screen of the song list.
**2.3.2 Database design**
admin table:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/7bf001e9-b3a7-4df0-9ff5-c6ef71ea8c75)

consumer table:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/cfcbc5d7-bf89-49f8-a445-fc6d5bab6297)

singer table:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/110b2205-c797-4486-b182-87f6830d3ae7)

song table:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/a1619aa5-8e1a-4515-baea-445b1ebff85c)

song list table:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/8a336fce-7439-40da-acf6-f58d6db501be)

list song table:

![image](https://github.com/susutingWW/MusicWebsite/assets/115572391/9d6142b0-97f6-4ad6-8b99-60d69efcecbd)
