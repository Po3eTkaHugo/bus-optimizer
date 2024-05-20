import axios from "axios";

let myMap;

let coords;
axios.defaults.baseURL = "http://localhost:8080/api/v1";
const btn = document.querySelector(".start-btn");
btn.addEventListener('click', handleClick);
function handleClick() {
  axios.get("/find_far_stops").then(response => {
    coords = response.data;
    console.log(coords);
    ymaps.ready(farStops);
  });
}

function farStops() {
  coords.forEach(function(segment) {
    var line = new ymaps.Polyline(
      segment,{},{
        strokeWidth: 5
      });
    myMap.geoObjects.add(line);
    console.log(segment)
  })
}

function init(){
  myMap = new ymaps.Map("map", {
    center: [51.66, 39.20],
    zoom: 12,
    controls: []
  });
}

ymaps.ready(init);
