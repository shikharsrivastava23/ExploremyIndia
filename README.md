A city tour sharing app using MapmyIndia APIs. 

## Figma 
Initial Figma Mockup: https://www.figma.com/file/RliN6KIzFIf36ZlZXwX0op/mockup

## Screenshots of the prototype app running on phhysical device


| <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.16%20(1).jpeg?sanitize=true&raw=true" width="165" />  | <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.16%20(2).jpeg" width="165" />  |  
|:-:|---|


| <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.16%20(4).jpeg" width="250" />  | <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.16%20(5).jpeg" width="250" />  | <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.16%20(6).jpeg" width="250" />  |<img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.16%20(7).jpeg" width="250" /> | <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.16%20(9).jpeg" width="250" /> |
|:-:|---|---|---|---|


| <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.16%20(3).jpeg" width="165" />  | <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.17.jpeg" width="165" />  |  <img src="https://raw.githubusercontent.com/shikharsrivastava23/ExploremyIndia/master/demo_images/WhatsApp%20Image%202020-08-31%20at%2023.28.17%20(1).jpeg" width="165" /> |
|:-:|---|---|





## Instructions to Run the prototype. 

1. Clone the entire repo, and run it using Android Studio. All the settings files are included, and the app should run out of the box.

2. The App uses Firebase Authentication and Realtime Database. 

3. You can sign up, by following the signup link on the first page, and then sign in using the newly created account. 

4. There are three main section

		i. Add a new experience (+ button on the bottom nav)
		ii. Explore (The compass button on the bottom nav) 
		iii. User profile (The rightmost tab in bottom nav)

5. The Cities in the Explore tab correspond to Lucknow, Delhi, Mumbai and Hyderabad (Clockwise Order starting top left)

6. These cities and the thumbnails are hardcoded in the prototype, so use will be able to view only these 4 cities for now. 

7. To create a new experience select the left most tab in the bottom navigation. 

        i. Enter a City name. The search bar is accompanied with AutoSuggest, with city filter. Only city names will be suggested. 
        ii. On the next page, user will be greeted with the map of the city previously entered. 
        iii. User can drag out the bottom sheet, which will reveal a search box, and two buttons. 
        iv. User can search for locations, then press Add location, this will add a pin on the corresponsing location on the map. 
        v. After Adding a couple of locations, User can proceed to add Details. 
        vi. In the next page, Give your new Tour a name, and add details for each of the locations. 
        vii. Clicking on publish will enter save the tour on the database. 

8. On the explore Tab, use can select any one city, and the lit of tours/experiences available in that city will be visible. 

9. Use can View itenary for any of these tours, The complete itinerary is visible on the map with dropped pins and a polyline joining them. The Bottom sheet also provides a list view of the complete itinerary. Each individual item can be clicked to reveal the details of that place. 

10. Finally, The use can logout of the app by visiting the third page on the bottom nav.   


