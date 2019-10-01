<?php
//header('Content-Type: text/html; charset=utf-8');
//date_default_timezone_set("Asia/Tehran");
require_once(getcwd() . '/system/db.php');
require_once(getcwd() . '/config.php');


$Key=$_REQUEST["Key"];


if($Key=="Places"){
    $db = new Db();
    $results = $db->query("SELECT * FROM places LEFT OUTER JOIN kinds ON places.kind_id LIKE kinds.kind_id");
    $db->close();
    echo json_encode($results);
}
if($Key=="Places_New"){
    $db = new Db();
    $results = $db->query("SELECT * FROM places ORDER BY DATE(place_date) DESC LIMIT 3");
    $db->close();
    echo json_encode($results);
}

if($Key=="Places_Offer"){
    $db = new Db();
    $results = $db->query("SELECT * FROM places ORDER BY (place_sort) DESC LIMIT 3");
    $db->close();
    echo json_encode($results);
}
if($Key=="Kinds"){
    $db = new Db();
    $results = $db->query("SELECT * FROM kinds ORDER BY (kind_sort) ASC ");
    $db->close();
    echo json_encode($results);
}
if($Key=="Places_Kinds"){
    $Kind=$_REQUEST["Kind"];
    $db = new Db();
    $results = $db->query("SELECT * FROM places WHERE  kind_id LIKE (:kind_id) ORDER BY (place_sort) DESC", array(
        'kind_id' => $Kind
    ));
    $db->close();
    echo json_encode($results);
}
if($Key=="Types"){
    $db = new Db();
    $results = $db->query("SELECT * FROM types ORDER BY (type_sort) ASC ");
    $db->close();
    echo json_encode($results);
}
if($Key=="Foods_Off"){
    $db = new Db();
    $results = $db->query("SELECT * FROM foods RIGHT outer join places ON foods.place_id = places.place_id GROUP BY places.place_id HAVING MAX(food_off) > 0");
    $db->close();
    echo json_encode($results);
}

if($Key=="Place_Foods"){
    $placeId=$_REQUEST["place_id"];
    $db = new Db();
    $results = $db->query("SELECT food_id,food_name,food_desc,food_price,food_off,food_stock,menu_name FROM foods LEFT OUTER JOIN menus ON foods.menu_id = menus.menu_id where foods.place_id LIKE (:place_id)", array(
        'place_id' => $placeId
    ));
    $db->close();
    echo json_encode($results);
}
///////////////////
if($Key=="Place_Comments"){
    $placeId=$_REQUEST["place_id"];
    $db = new Db();
    $results = $db->query("SELECT order_list,order_date,comments_comment,comments_rate,user_name FROM orders LEFT OUTER JOIN comments ON orders.order_id = comments.order_id
 LEFT OUTER JOIN users ON orders.user_id = users.user_id where orders.place_id LIKE (:place_id)", array(
        'place_id' => $placeId
    ));
    $db->close();
    echo json_encode($results);
}
