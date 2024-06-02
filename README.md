# 语雀-markdown

## 1. 介绍

### 1.1 基本概况
为了解决语雀公开文档无法下载的问题而创建该项目，将语雀的公开文档下载为markdown以便于本地浏览（不需要登录和token）。

### 1.2 技术栈
JavaFx + Selenium，内置谷歌浏览器和驱动。

## 2. 教程

### 2.1 使用
1.点击tags-Releases-选择对应的版本下载，然后解压，打开文件夹lark-markdown，运行lark-markdown.exe

![image](https://github.com/xzc-coder/lark-markdown/assets/34028866/37c8d7f4-6203-4311-bcd4-dc7e599bc5d2)

2.点击右上角设置，选择下载后保存目录

![image](https://github.com/xzc-coder/lark-markdown/assets/34028866/7c02721d-de26-4236-a884-fe8ff7678590)

3.文章URL请填写语雀的公开文档路径，如：https://www.yuque.com/u21195183/jvm/rq9lt4  之后点击添加，即会添加到下载队列

![image](https://github.com/xzc-coder/lark-markdown/assets/34028866/59bb12bd-f83e-4289-ac8b-bc26a8d830a2)

4.当状态为完成时，会生成对应标题的文件夹

![image](https://github.com/xzc-coder/lark-markdown/assets/34028866/be392d69-328d-4544-b59e-d0b7d4e0758b)


## 3. 其它

### 3.1 注意事项
1.目前仅支持windows（64位）

2.仅支持公开文档（不需要登录和token）

3.目前仅支持markdown的基础语法

4.如果一直超时失败，可调大缓冲时间和滚动间隔

5.暂不支持解压到中文文件夹下使用（后续解决）

### 3.2 issue
如果产生问题，请提issue，并且提供产生问题的文档URL，以便排查。

格式：问题描述+文档URL

### 3.3 补充
1.因为内置了谷歌浏览器和驱动，所以压缩包比较大（不内置的话过于麻烦，主要是为了减少用户设置）

2.如果觉得好用，可以点个star

3.暂时不更新了，准备做个浏览器插件解析！！！！
