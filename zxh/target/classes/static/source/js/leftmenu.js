//加载日志博客类别
$(function () {
    //获取该路径下的一个json格式的字符串
    $.get("/blogtype/typelist",function (data) {

        //获取到common.html中的 blogTypeList
        var blogTypeList=$("#blogTypeList");

        //循环遍历json数组
        $(data).each(function () {
            /*会自动把typeid  封装到 controller中的 bloginformation中*/
            var li= "<li><span><a href='/index?typeid="+this.id+"'>"+this.typename+"("+this.blogcount+")</a></span></li>";

            //将每一个li 加到 ul标签中
            $(blogTypeList).append(li);
        });
    },"json");

    $.get("/bloginformation/datecount",function (data) {

        var dateList=$("#dateList");
        $(data).each(function () {
            var li= "<li><span><a href='/index?date="+this.date+"'>"+this.date+"("+this.datecount+")</a></span></li>";

            //将每一个li 加到 ul标签中
            $(dateList).append(li);
        });

    },"json");
})