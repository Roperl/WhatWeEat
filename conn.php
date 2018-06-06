<?php
/**
 * Created by PhpStorm.
 * User: Roper
 * Date: 2018/4/27
 * Time: 21:04
 */
$conn=mysql_connect("localhost","root","123456")or die("数据库连接失败");
mysql_select_db("restaurants",$conn);
mysql_query("set names utf8");