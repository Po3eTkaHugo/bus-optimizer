function init(){
  var multiRoute = new ymaps.multiRouter.MultiRoute({
    referencePoints: [
      [51.7237811, 39.1564149],
      [51.725466918, 39.161615012],
      [51.729520422, 39.168936189]
    ]
  }, {
    wayPointVisible: false
  });

  var multiRouteAnother = new ymaps.multiRouter.MultiRoute({
    referencePoints: [
      [51.709688,  39.1813],
      [51.717697157,  39.180303202],
      [51.723417775, 39.179625682],
      [51.727941345, 39.17917702]
    ],
    params: {
      routingMode: 'masstransit',
      avoidPedestrianPaths: true
    }
  },{
    wayPointVisible: false
  });

  var myMap = new ymaps.Map("map", {
    center: [51.66, 39.20],
    zoom: 12,
    controls: []
  });

  //myMap.geoObjects.add(multiRoute);
  myMap.geoObjects.add(multiRouteAnother);
}

ymaps.ready(init);