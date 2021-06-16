# Android-Projects
Android课程的入门练习，用作期末考试复习。

知识点参考《第一行代码Android 第二版》。

 

1. 请实现以下两个界面，并完成从(a)界面传值跳转到(b)界面的过程。要求是：至少在一个activity里面的主要生命周期函数中，用Log输出一句测试的话，并对Log画面进行截图；对两个Activity传值效果进行截图。

   Tips：主队和客队选择必须和小组进行联动。主队和客队一样时，不能预测。

   

2. 请编写一个界面，里面有一个按钮和三个文本框。点击按钮访问http://115.29.231.93:8080/CkeditorTest/AndroidTest, 具体的参数是`userId=xxx&style=json`, 其中xxx是你自己的学号。返回值也是json格式，例如`{"returnCode":"0";"returnValue":"bbb";”returnMsg”:”ccc”}`。解析返回值，如果returnCode大于0，请将returnCode和returnValue显示在输入框中；如果returnCode小于0，请将returnCode和returnMsg显示在相应的输入框中。

   

3. 访问手机通讯录，并用ListView显示出来，列表中显示姓名,电话。点击单条，弹出对话框，列出姓名，电话和邮件，并实现2个按钮的功能：返回和拨打。

   

4. 在Activity界面上能够以文字形式显示当前所在地的GPS地址、省市区街道等地址，以及标注所在的地图。界面上同时有一个按钮，点击前往华东师范大学，地图将切换到华东师范大学位置(121.412119,31.233168)。

   

5. 在Activity上点击按钮，发送一条系统全局广播（带参数100和hello），接收方收到广播后，显示一条通知。点击通知，跳转到B Activity，显示参数值。

   

6. 左滑菜单，分别有计软学院，教育学部，经管学部三个菜单项，点击每个菜单项，在内容区可以加载网页，出现相应的三个学院/学部介绍（网址分别是`www.sei.ecnu.edu.cn`, `www.ed.ecnu.edu.cn`; `fem.ecnu.edu.cn`）。

   

7. 每隔10秒，产生一条toast，显示hello和系统时间。在Activity界面上设置两个按钮，可以启动和停止Service。

   

8. 以RecycleView显示data.txt日志记录(自己模拟，格式：时间,标题,事件描述)。列表中显示时间和标题。点击单条，弹出对话框，显示事件描述。选择删除，列表中清空一条记录。

   

9. （综合题）两个界面。界面A有两个按钮。点击“加载”按钮，将通讯录内容读出，放在Sqlite中，并显示成功。点击显示按钮，跳转到界面B，将所有的通讯录按照名字顺序显示出来。

   

10. （综合题）界面A包含一个按钮和一个文本框。点击按钮启动新线程进行耗时操作(产生一个随机字符串后休眠10秒)。此时文本框显示“数据等待中”。新线程完成休眠后，将字符串保存到Sqlite，并在主界面文本框显示该字符串。

    

11. （综合题）在主界面上设置两个按钮，还有一个文本区，可以启动和停止Service。该Service每隔20秒产生一个随机数，并存储到Sqlite中。当点击终止按钮后，在文本区显示这些随机数的总和。

