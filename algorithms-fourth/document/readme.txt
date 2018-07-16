Algorithms, 4th Edition
https://algs4.cs.princeton.edu/home/

In in = new In(args[0]);像这种情况一般需要输入一个文件

有两种方法
	第一种办法就是把文件复制到工程的根目录，然后点击Run As-->Run Configurations-->Arguments,直接输入文件名就可以了。
	第二种办法就是Run As-->Run Configurations-->Commom-->Input File;在Input File里面输入要读取的文本文件，然后在Arguments,直接输入文件名及其所在的目录就可以了。

而StdIn则是一般在控制窗口输入的，最后一般Ctr+d（linux系统下）或则Ctr+z（windows系统下）结束。

像还有args[1],args[2]的，看需要输入的是什么类型的参数，就所在Run As-->Run Configurations-->Arguments中输入就可以了。

最后提醒下：注意输入的顺序，按照arg[0],args[1],args[2]顺序来。