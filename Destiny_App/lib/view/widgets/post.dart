import 'dart:convert';

import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/material.dart';
import 'package:login_signup/utils/api.dart';
import 'package:login_signup/utils/gobal.colors.dart';
import 'package:login_signup/view/edit_profile.view.dart';
import 'package:login_signup/view/screens/profile.view.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;

class Post extends StatefulWidget {
  const Post({super.key});

  @override
  State<Post> createState() => _PostState();
}

String? ava;
String? fullname;
int? countPost;
int? countFollower;
int? countImg;
List? listPost;

List<String> imagesList = [
  "https://static.wikia.nocookie.net/doraemon/images/8/8d/Doraemon_%282017_Remake%29.png/revision/latest?cb=20230908064228&path-prefix=en",
  "https://i.pinimg.com/736x/0f/4d/a8/0f4da8a2e550bd047de21ab679cfa8fa.jpg",
  "https://kenh14cdn.com/203336854389633024/2022/11/15/photo-6-16684998819951103587151.jpg",
  "https://i.pinimg.com/originals/d4/0e/07/d40e07b9c3e106922860500dca917cad.jpg",
  "https://kenh14cdn.com/thumb_w/600/2019/9/1/chaien-15672732658971052114509-crop-1567273272747169033775.png",
];

class _PostState extends State<Post> {
  @override
  void initState() {
    super.initState();
    this.getData();
  }

  Future getData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    var value = await prefs.getString('token');
    var headers = {
      'Authorization': ' Bearer $value',
      'Content-Type':
          'application/json', // Điều này phụ thuộc vào yêu cầu cụ thể của máy chủ
    };
    var id_user = await prefs.getInt('id');
    var dataUser = {'toProfile': id_user, 'page': "1"};
    http.Response response;

    response = await http.post(
        Uri.parse(ApiEndPoints.baseUrl + "v1/user/profile/post/timeline"),
        headers: headers,
        body: jsonEncode(dataUser));
    if (response.statusCode == 200) {
      setState(() {
        List data = jsonDecode(Utf8Decoder().convert(response.bodyBytes));
        listPost = data;
        print(listPost);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Container(
          height: 480,
          margin: EdgeInsets.only(top: 10),
          width: MediaQuery.of(context).size.width,
          color: Colors.white,
          child: Column(children: [
            Expanded(
              child: ListView(
                children: <Widget>[
                  for (int i = 0; i < listPost!.length; i++)
                    Container(
                      color: Colors.white,
                      padding: EdgeInsets.only(
                          // top: 4,
                          bottom:
                              10), // Thêm padding để tạo khoảng cách giữa các bài viết
                      margin: EdgeInsets.only(bottom: 10),
                      child: Column(
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Row(
                                children: [
                                  Container(
                                    margin: EdgeInsets.only(left: 10, top: 10),
                                    child: InkWell(
                                      onTap: () async {
                                        SharedPreferences prefs =
                                            await SharedPreferences
                                                .getInstance();

                                        await prefs.setInt(
                                            'id', listPost![i]['user_id']);
                                        runApp(GetMaterialApp(
                                          home: ProfileView(),
                                        ));
                                      },
                                      child: CircleAvatar(
                                        backgroundImage: NetworkImage(
                                            listPost![i]['avatar']),
                                      ),
                                    ),
                                  ),
                                  Container(
                                      margin:
                                          EdgeInsets.only(left: 10, top: 10),
                                      child: listPost![i]
                                                  ['postEntityProfile'] ==
                                              null
                                          ? Text(
                                              listPost![i]['fullname'],
                                              style: TextStyle(
                                                fontSize: 15,
                                                fontWeight: FontWeight.w500,
                                              ),
                                            )
                                          : ConstrainedBox(
                                              constraints: BoxConstraints(
                                                maxWidth: MediaQuery.of(context)
                                                        .size
                                                        .width *
                                                    0.75,
                                              ),
                                              child: Text(
                                                listPost![i]['fullname'] +
                                                    " đã chia sẻ bài viết của " +
                                                    listPost![i][
                                                            'postEntityProfile']
                                                        ['fullname'],
                                                style: TextStyle(fontSize: 15),
                                              ),
                                            ))
                                ],
                              ),
                              Container(
                                margin: EdgeInsets.only(right: 10),
                                child: PopupMenuButton(
                                  itemBuilder: (context) => [
                                    PopupMenuItem(
                                      onTap: () {
                                        Navigator.pushNamed(context, "/");
                                      },
                                      child: Row(
                                        children: [
                                          Icon(Icons.report),
                                          Padding(
                                            padding:
                                                EdgeInsets.only(left: 10.0),
                                            child: Text("Báo cáo bài viết"),
                                          ),
                                        ],
                                      ),
                                    ),
                                    PopupMenuItem(
                                      onTap: () {
                                        Navigator.pushNamed(context, "/");
                                      },
                                      child: Row(
                                        children: [
                                          Icon(Icons.report_off_outlined),
                                          Padding(
                                            padding:
                                                EdgeInsets.only(left: 10.0),
                                            child: Text("Báo cáo tài khoản"),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ],
                                  child: Icon(
                                    Icons.more_horiz,
                                    color: GlobalColors.mainColor,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          SizedBox(
                            height: 10,
                          ),
                          listPost![i]['postEntityProfile'] == null
                              ? Column(
                                  children: [
                                    ConstrainedBox(
                                      constraints: BoxConstraints(
                                        maxWidth:
                                            MediaQuery.of(context).size.width,
                                      ),
                                      child: Text(
                                        listPost![i]['content'],
                                        style: TextStyle(fontSize: 15),
                                      ),
                                    ),
                                    SizedBox(
                                      height: 10,
                                    ),
                                    ConstrainedBox(
                                      constraints: BoxConstraints(
                                        minHeight: 150,
                                        minWidth: 150,
                                        maxHeight: 350.0,
                                        maxWidth:
                                            MediaQuery.of(context).size.width,
                                      ),
                                      child: CarouselSlider(
                                        items: (listPost![i]['images']
                                                    as List<dynamic>?)
                                                ?.map<Widget>((imageData) {
                                              if (imageData is String) {
                                                return ClipRRect(
                                                  borderRadius:
                                                      BorderRadius.circular(6),
                                                  child: Image.network(
                                                    imageData,
                                                    height: 200,
                                                    width: 350,
                                                    fit: BoxFit.cover,
                                                  ),
                                                );
                                              }
                                              return SizedBox
                                                  .shrink(); // Nếu dữ liệu hình ảnh không hợp lệ, hiển thị Widget trống
                                            })?.toList() ??
                                            [],
                                        options: CarouselOptions(
                                            autoPlay: false,
                                            enableInfiniteScroll: false,
                                            enlargeCenterPage: true,
                                            height: 320),
                                      ),
                                    ),
                                    Row(
                                      mainAxisSize: MainAxisSize.max,
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        Container(
                                          margin: EdgeInsets.only(top: 10),
                                          alignment: Alignment.center,
                                          height: 50,
                                          width: MediaQuery.of(context)
                                                  .size
                                                  .width *
                                              0.33,
                                          child: Container(
                                            width: 100,
                                            child: Row(
                                              children: [
                                                Container(
                                                  alignment: Alignment.center,
                                                  width: 30,
                                                  child: Icon(
                                                    Icons.favorite_border,
                                                    color: Colors.red,
                                                  ),
                                                ),
                                                Container(
                                                    alignment: Alignment.center,
                                                    width: 20,
                                                    child: Text(listPost![i]
                                                            ['countInterested']
                                                        .toString())),
                                              ],
                                            ),
                                          ),
                                        ),
                                        Container(
                                          margin: EdgeInsets.only(top: 10),
                                          alignment: Alignment.center,
                                          height: 50,
                                          width: MediaQuery.of(context)
                                                  .size
                                                  .width *
                                              0.33,
                                          child: Container(
                                            width: 100,
                                            child: Row(
                                              children: [
                                                Container(
                                                  alignment: Alignment.center,
                                                  width: 30,
                                                  child: Icon(
                                                    Icons.comment,
                                                    color: Colors.green,
                                                  ),
                                                ),
                                                Container(
                                                  alignment: Alignment.center,
                                                  width: 60,
                                                  child: Text("Bình luận"),
                                                ),
                                              ],
                                            ),
                                          ),
                                        ),
                                        Container(
                                          margin: EdgeInsets.only(top: 10),
                                          alignment: Alignment.center,
                                          height: 50,
                                          width: MediaQuery.of(context)
                                                  .size
                                                  .width *
                                              0.33,
                                          child: Container(
                                            width: 100,
                                            child: Row(
                                              children: [
                                                Container(
                                                  alignment: Alignment.center,
                                                  width: 30,
                                                  child: Icon(
                                                    Icons.share,
                                                    color: const Color.fromARGB(
                                                        255, 2, 124, 224),
                                                  ),
                                                ),
                                                Container(
                                                  alignment: Alignment.center,
                                                  width: 50,
                                                  child: Text("Chia sẻ"),
                                                ),
                                              ],
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ],
                                )
                              : Container(
                                  color: Colors.white,
                                  padding: EdgeInsets.only(
                                      // top: 4,
                                      bottom:
                                          10), // Thêm padding để tạo khoảng cách giữa các bài viết
                                  margin: EdgeInsets.only(bottom: 10),
                                  child: Column(
                                    children: [
                                      Row(
                                        mainAxisAlignment:
                                            MainAxisAlignment.spaceBetween,
                                        children: [
                                          Row(
                                            children: [
                                              Container(
                                                margin: EdgeInsets.only(
                                                    left: 10, top: 10),
                                                child: InkWell(
                                                  onTap: () async {
                                                    SharedPreferences prefs =
                                                        await SharedPreferences
                                                            .getInstance();

                                                    await prefs.setInt(
                                                        'id',
                                                        listPost![i][
                                                                'postEntityProfile']
                                                            ['user_id']);
                                                    runApp(GetMaterialApp(
                                                      home: ProfileView(),
                                                    ));
                                                  },
                                                  child: CircleAvatar(
                                                    backgroundImage:
                                                        NetworkImage(listPost![
                                                                    i][
                                                                'postEntityProfile']
                                                            ['avatar']),
                                                  ),
                                                ),
                                              ),
                                              Container(
                                                  margin: EdgeInsets.only(
                                                      left: 10, top: 10),
                                                  child: Text(
                                                    listPost![i][
                                                            'postEntityProfile']
                                                        ['fullname'],
                                                    style: TextStyle(
                                                      fontSize: 15,
                                                      fontWeight:
                                                          FontWeight.w500,
                                                    ),
                                                  ))
                                            ],
                                          ),
                                          Container(
                                            margin: EdgeInsets.only(right: 10),
                                            child: PopupMenuButton(
                                              itemBuilder: (context) => [
                                                PopupMenuItem(
                                                  onTap: () {
                                                    Navigator.pushNamed(
                                                        context, "/");
                                                  },
                                                  child: Row(
                                                    children: [
                                                      Icon(Icons.report),
                                                      Padding(
                                                        padding:
                                                            EdgeInsets.only(
                                                                left: 10.0),
                                                        child: Text(
                                                            "Báo cáo bài viết"),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                                PopupMenuItem(
                                                  onTap: () {
                                                    Navigator.pushNamed(
                                                        context, "/");
                                                  },
                                                  child: Row(
                                                    children: [
                                                      Icon(Icons
                                                          .report_off_outlined),
                                                      Padding(
                                                        padding:
                                                            EdgeInsets.only(
                                                                left: 10.0),
                                                        child: Text(
                                                            "Báo cáo tài khoản"),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ],
                                              child: Icon(
                                                Icons.more_horiz,
                                                color: GlobalColors.mainColor,
                                              ),
                                            ),
                                          ),
                                        ],
                                      ),
                                      SizedBox(
                                        height: 10,
                                      ),
                                      Column(
                                        children: [
                                          ConstrainedBox(
                                            constraints: BoxConstraints(
                                              maxWidth: MediaQuery.of(context)
                                                  .size
                                                  .width,
                                            ),
                                            child: Text(
                                              listPost![i]['postEntityProfile']
                                                  ['content'],
                                              style: TextStyle(fontSize: 15),
                                            ),
                                          ),
                                          SizedBox(
                                            height: 10,
                                          ),
                                          ConstrainedBox(
                                            constraints: BoxConstraints(
                                              minHeight: 150,
                                              minWidth: 150,
                                              maxHeight: 350.0,
                                              maxWidth: MediaQuery.of(context)
                                                  .size
                                                  .width,
                                            ),
                                            child: CarouselSlider(
                                              items:
                                                  (listPost![i]['postEntityProfile']
                                                                  ['images']
                                                              as List<dynamic>?)
                                                          ?.map<Widget>(
                                                              (imageData) {
                                                        if (imageData
                                                            is String) {
                                                          return ClipRRect(
                                                            borderRadius:
                                                                BorderRadius
                                                                    .circular(
                                                                        6),
                                                            child:
                                                                Image.network(
                                                              imageData,
                                                              height: 200,
                                                              width: 350,
                                                              fit: BoxFit.cover,
                                                            ),
                                                          );
                                                        }
                                                        return SizedBox
                                                            .shrink(); // Nếu dữ liệu hình ảnh không hợp lệ, hiển thị Widget trống
                                                      })?.toList() ??
                                                      [],
                                              options: CarouselOptions(
                                                  autoPlay: false,
                                                  enableInfiniteScroll: false,
                                                  enlargeCenterPage: true,
                                                  height: 320),
                                            ),
                                          ),
                                          Row(
                                            mainAxisSize: MainAxisSize.max,
                                            mainAxisAlignment:
                                                MainAxisAlignment.center,
                                            children: [
                                              Container(
                                                margin:
                                                    EdgeInsets.only(top: 10),
                                                alignment: Alignment.center,
                                                height: 50,
                                                width: MediaQuery.of(context)
                                                        .size
                                                        .width *
                                                    0.33,
                                                child: Container(
                                                  width: 100,
                                                  child: Row(
                                                    children: [
                                                      Container(
                                                        alignment:
                                                            Alignment.center,
                                                        width: 30,
                                                        child: Icon(
                                                          Icons.favorite_border,
                                                          color: Colors.red,
                                                        ),
                                                      ),
                                                      Container(
                                                          alignment:
                                                              Alignment.center,
                                                          width: 20,
                                                          child: Text(listPost![
                                                                          i][
                                                                      'postEntityProfile']
                                                                  [
                                                                  'countInterested']
                                                              .toString())),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                              Container(
                                                margin:
                                                    EdgeInsets.only(top: 10),
                                                alignment: Alignment.center,
                                                height: 50,
                                                width: MediaQuery.of(context)
                                                        .size
                                                        .width *
                                                    0.33,
                                                child: Container(
                                                  width: 100,
                                                  child: Row(
                                                    children: [
                                                      Container(
                                                        alignment:
                                                            Alignment.center,
                                                        width: 30,
                                                        child: Icon(
                                                          Icons.comment,
                                                          color: Colors.green,
                                                        ),
                                                      ),
                                                      Container(
                                                        alignment:
                                                            Alignment.center,
                                                        width: 60,
                                                        child:
                                                            Text("Bình luận"),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                              Container(
                                                margin:
                                                    EdgeInsets.only(top: 10),
                                                alignment: Alignment.center,
                                                height: 50,
                                                width: MediaQuery.of(context)
                                                        .size
                                                        .width *
                                                    0.33,
                                                child: Container(
                                                  width: 100,
                                                  child: Row(
                                                    children: [
                                                      Container(
                                                        alignment:
                                                            Alignment.center,
                                                        width: 30,
                                                        child: Icon(
                                                          Icons.share,
                                                          color: const Color
                                                              .fromARGB(
                                                              255, 2, 124, 224),
                                                        ),
                                                      ),
                                                      Container(
                                                        alignment:
                                                            Alignment.center,
                                                        width: 50,
                                                        child: Text("Chia sẻ"),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                            ],
                                          ),
                                        ],
                                      )
                                    ],
                                  ),
                                ),
                        ],
                      ),
                    ),
                ],
              ),
            ),
          ])),
    );
  }
}