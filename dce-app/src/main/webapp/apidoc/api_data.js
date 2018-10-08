define({ "api": [
  {
    "type": "GET",
    "url": "/aboutUs/index.do",
    "title": "项目介绍列表",
    "name": "ysAboutList",
    "group": "YsAbout",
    "version": "1.0.0",
    "description": "<p>项目介绍列表</p>",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "model",
            "description": "<p>返回成功信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "url",
            "description": "<p>介绍地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "resultCode",
            "description": "<p>结果码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "errorMessage",
            "description": "<p>错误消息说明</p>"
          },
          {
            "group": "Success 200",
            "type": "Boolean",
            "optional": false,
            "field": "success",
            "description": "<p>是否成功</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"result\": {\n\t\"model\": {\n\t\t\n\t},\n\t  \"status\": {\n\t    \"code\": 200,\n\t    \"msg\": \"请求成功\"\n\t  }\n\t}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/aboutUs/AboutUsController.java",
    "groupTitle": "YsAbout",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/aboutUs/index.do"
      }
    ]
  }
] });
