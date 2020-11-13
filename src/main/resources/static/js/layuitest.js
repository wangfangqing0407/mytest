/*layui.use(['layer', 'form'], function(){
    var layer = layui.layer,
        form = layui.form;

    layer.msg('Hello World');
});*/
layui.use('upload', function () {
    console.log("123");
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#upLoad', //绑定元素
        url: '/admin/uploadFile', //上传接口
        headers:'application/json;charset=UTF-8',
        size: 1000,
        exts: 'jpg|png|gif|bmp|jpeg|xlsx',
        size:'204800', //kb
        before: function(){
            console.log('接口地址：'+ this.url, this.item, {tips: 1});
        },
        /* done: function (r) {
         //layer.msg(r.msg);
         if(r.code == 0){
         parent.layer.msg(data.msg);
         }
         // app.getData().code;
         }, */
        done: function(res, index, upload){
            console.log("success");
        },
        error: function (r) {
            console.log("faile");
        }
    });

});
$(function() {
    $("#downLoad").bind("click", function() {
        console.log("download");
        location.href="/admin/downloadFile";
    });
});
$(function() {
    $("#downLoad2").bind("click", function() {
        console.log("download2");
        location.href="/admin/downloadFileTwo";
    });
});
