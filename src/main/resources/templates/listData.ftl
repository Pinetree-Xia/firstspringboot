<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>


    <style>
        th {
            padding: 10px 13px;
            border: 1px solid #ddd;
        }

    </style>

</head>


<body bgcolor="#f5f5dc">

<div style="padding: 20px;">
    <a href="queryBTC?start=201701011100&end=201702181100" target="_blank"> 查看 web service json </a>
</div>

<form   action="./listData" style="padding: 20px;">
    <input type="text" name="queryDateStr" size="10" maxlength="10" value="${curDate}">
    <input type="submit" name="submit" value="查询">
</form>


<table bgcolor="#faebd7">
    <tr>
        <th>
            日期
        </th>
        <th>
            成交额
        </th>
    </tr>
<#list datePOList as item>
    <tr>
        <th>
             ${item.minute}
        </th>
        <th>
             ${item.summerPerDay}
        </th>
    </tr>
</#list>
</table>



</body>
</html>