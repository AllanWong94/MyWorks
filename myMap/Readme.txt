Intro:
	This myMap project is a web mapping application developed by Allan Wong and the teaching staff of course CS61B in UC Berkeley led by Josh Hug. Allan implemented the backend and the staff set up the frontend to send quests and render the webpage.
	The map include features as follows.
	1) Rendering
		The program is able to render the map within the surrounding of the UC Berkeley campus, supporting navigation such as shifting and zoom in/out.
	2) Routing
		The map learn the locations in the map by reading in an OpenStreetMap file that contains the location data of the environment. Then, with the A* algorithm, it is able to find a route of minimal length between two designated locations with efficiency. Also, after the route is found, the map will draw the route on the map.
	3) Auto-completion and Search
		The OpenStreeMap data contains names for locations, of which the map make use of to realize the function of auto-completion. It is able to list out all the names that has a prefix the same as inputted by the user. Then by selecting among the options the application will paint out the location of the user's selection on the map.

Usage:
	1)Start
		Run MapServer.java under ./src/main/java
	2)Connect
		Open a browser. Type "localhost:4567" in the address bar. Then you should see the map in the browser.
	3)Navigate
		Use mouse to drag or arrow keys to shift the map in any direction. Use "+" key or "-" key or mousewheel up/down to zoom in or zoom out.
	4)Route
		Double click a position to designate a starting point. Repeat the action on another position to designate an ending point. Then the application will calculate the fastest route using applicable roads (excluding one-way traffic or pavement as this function is indicated for vehicles) and then render the route on the map in blue lines.
	5)Clear-route
		Click on the "Clear-Route" button on the top-right corner to clear the route record.
	6)Auto-completion and search
		Type in the "Search for:" bar on the top-left corner of the webpage to receive a list of names of locations that have a prefix of your input. Click on one of the names so that the application will paint out the location(s) of your selection on the map. Note that there may be multiple locations as some places share a name.

To be improved:
	1)Performance. The rendering and routing function works fine. But they both takes time up to 3 seconds to handle a quest. The data structure and work flow should be optimized to decrease the processing time.
	2)Generalization. The map is limited around UC Berkeley. I intend to make the map functional on the surrounding of my hometown, then maybe generalize it to be the whole province, or probably more, just for fun.
	3)Interaction. Though the icons and bars finish the job, they can still be replaced by elements that are more intuitional and elegant.
