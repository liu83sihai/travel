define({ "api": [
  {
    "type": "GET",
    "url": "/aboutUs/index.do",
    "title": "项目介绍",
    "name": "aboutUs",
    "group": "aboutUs",
    "version": "1.0.0",
    "description": "<p>项目介绍</p>",
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
            "field": "msg",
            "description": "<p>返回成功信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回成功编码</p>"
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
    "groupTitle": "aboutUs",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/aboutUs/index.do"
      }
    ]
  },
  {
    "type": "GET",
    "url": "/banner/index.do",
    "title": "图标管理列表",
    "name": "bannerList",
    "group": "banner",
    "version": "1.0.0",
    "description": "<p>图标管理列表</p>",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>返回成功信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回成功编码</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "icoName",
            "description": "<p>图标名称</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "icoImage",
            "description": "<p>图片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "icoType",
            "description": "<p>图标类型（1:banner图,2:导航小图标）</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "linkType",
            "description": "<p>链接类型（0:无 1:超链接  2:程序链接）</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "linkValue",
            "description": "<p>链接值</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "hintValue",
            "description": "<p>提示内容</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "sort",
            "description": "<p>排序</p>"
          },
          {
            "group": "Success 200",
            "type": "java.sql.Date",
            "optional": false,
            "field": "createDate",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "createName",
            "description": "<p>创建人</p>"
          },
          {
            "group": "Success 200",
            "type": "java.sql.Date",
            "optional": false,
            "field": "modifyDate",
            "description": "<p>更新时间</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "modifyName",
            "description": "<p>更新人</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "status",
            "description": "<p>状态(0:删除  1:正常)</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "remark",
            "description": "<p>备注</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\ticoName ico_name\n\t\t\t\ticoImage ico_image\n\t\t\t\ticoType ico_type\n\t\t\t\tlinkType link_type\n\t\t\t\tlinkValue link_value\n\t\t\t\thintValue hint_value\n\t\t\t\tsort sort\n\t\t\t\tcreateDate create_date\n\t\t\t\tcreateName create_name\n\t\t\t\tmodifyDate modify_date\n\t\t\t\tmodifyName modify_name\n\t\t\t\tstatus status\n\t\t\t\tremark remark\n\t\t\t}\n\t\t]\n\t  }\n\t}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/banner/BannerController.java",
    "groupTitle": "banner",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/banner/index.do"
      }
    ]
  },
  {
    "type": "GET",
    "url": "/notice/list.do",
    "title": "公告列表",
    "name": "notice",
    "group": "notice",
    "version": "1.0.0",
    "description": "<p>公告列表</p>",
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
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>公告id</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "top_notice",
            "description": "<p>置顶公告（0是/1否)</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>公告标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>公告类容</p>"
          },
          {
            "group": "Success 200",
            "type": "date",
            "optional": false,
            "field": "create_date",
            "description": "<p>创建时间</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "image",
            "description": "<p>列表图</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>返回成功信息</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回成功编码</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n\"code\": 0,\n\"msg\": \"请求成功\"\n\"data\":{\n  \t[\n  \t{\"id\": \"1\",\n  \t \"topNotice\":\"0\",\n  \t \"title\": \"12154545\",\n  \t \"content\": \"12321\",\n  \t \"create_date\": 2018-8-6 14:55 ,\n  \t \"image\": \"c:/img/xx.jpg\",\n  \t},\n  \t{\"id\": \"1\",\n  \t \"topNotice\":\"0\",\n  \t \"title\": \"12154545\",\n  \t \"content\": \"12321\",\n  \t \"create_date\": 2018-8-6 14:55 ,\n  \t \"image\": \"c:/img/xx.jpg\",\n  \t}\n   ]\n }\n}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/action/notice/NoticeController.java",
    "groupTitle": "notice",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/notice/list.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/user/shareList.do",
    "title": "分享排榜",
    "name": "shareList",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>分享排榜</p>",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>返回成功信息</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "id",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "userName",
            "description": "<p>用户名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "trueName",
            "description": "<p>真名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "refereeNumber",
            "description": "<p>推荐人数</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>返回成功编码</p>"
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
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/user/shareList.do"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "rows",
            "description": "<p>展示的条数</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "pageNum",
            "description": "<p>页码</p>"
          }
        ]
      }
    }
  }
] });
