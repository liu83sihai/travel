define({ "api": [
  {
    "type": "get",
    "url": "/rest/commonIntf/sendMessage",
    "title": "获取短信验证码",
    "name": "sendMessage",
    "group": "Common",
    "version": "1.0.0",
    "description": "<p>根据手机号码返回短信验证码</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "model",
            "description": "<p>验证码</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n\t\"model\": \"760428\",\n\t\"success\": true,\n\t\"errorMessage\": null,\n\t\"resultCode\": 200\n}",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "301",
            "description": "<p>对应<code>301</code> 手机号码为空</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "302",
            "description": "<p>对应<code>302</code> 短信发送失败</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "405",
            "description": "<p>对应<code>405</code> 远程服务器调用失败</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response: ",
          "content": " 手机号码为空\n{\n\t\"model\": null,\n\t\"success\": false,\n\t\"errorMessage\": 手机号码为空,\n\t\"resultCode\": 301\n}",
          "type": "json"
        },
        {
          "title": "Response 405 Example",
          "content": "  HTTP/1.1 405 Internal Server Error\n  {\n\t\t\"model\": null,\n\t\t\"success\": false,\n\t\t\"errorMessage\": 远程服务器调用失败,\n\t\t\"resultCode\": 405\n  }",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/common/CommonIntf.java",
    "groupTitle": "Common",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/rest/commonIntf/sendMessage"
      }
    ]
  },
  {
    "type": "post",
    "url": "/rest/commonIntf/uploadImg",
    "title": "上传文件",
    "name": "uploadImg",
    "group": "Common",
    "version": "1.0.0",
    "description": "<p>上传图片接口,返回绝对路径,都是指定以png后辍结尾</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "MultipartFile",
            "optional": false,
            "field": "fileName",
            "description": "<p>文件数据流</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "filePath",
            "description": "<p>文件保存的绝对路径</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n\t\"model\": {\n\t\t\"filePath\": \"/upload/sc/images/20161227005210KOI5P4ew.png\",\n\t\t\"viewPath\": \"http://127.0.0.1:90/upload/sc/images/20161227005210KOI5P4ew.png\"\n\t},\n\t\"success\": true,\n\t\"errorMessage\": null,\n\t\"resultCode\": 200\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "305",
            "description": "<p>对应<code>305</code> 图片保存失败</p>"
          },
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "405",
            "description": "<p>对应<code>405</code> 远程服务器调用失败</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Response 405 Example",
          "content": "  HTTP/1.1 405 Internal Server Error\n  {\n\t\t\"model\": null,\n\t\t\"success\": false,\n\t\t\"errorMessage\": 远程服务器调用失败,\n\t\t\"resultCode\": 405\n  }",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/common/CommonIntf.java",
    "groupTitle": "Common",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/rest/commonIntf/uploadImg"
      }
    ]
  },
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
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
    "url": "/activity/index.do",
    "title": "活动风彩列表",
    "name": "activityList",
    "group": "activity",
    "version": "1.0.0",
    "description": "<p>活动风彩列表</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 用户ID\n\t\t\t\tsynopsis 描述\n\t\t\t\tcontent 内容\n\t\t\t\timages 图片\n\t\t\t\thitNum 点赞数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
          "type": "json"
        }
      ],
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "synopsis",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "images",
            "description": "<p>图片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "hitNum",
            "description": "<p>点赞数</p>"
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
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "remark",
            "description": "<p>备注</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      }
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/activity/index.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/activity/add.do",
    "title": "添加活动风彩",
    "name": "addActivity",
    "group": "activity",
    "version": "1.0.0",
    "description": "<p>添加活动风彩</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "images",
            "description": "<p>图片</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 用户ID\n\t\t\t\tsynopsis 描述\n\t\t\t\tcontent 内容\n\t\t\t\timages 图片\n\t\t\t\thitNum 点赞数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
          "type": "json"
        }
      ],
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "synopsis",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "images",
            "description": "<p>图片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "hitNum",
            "description": "<p>点赞数</p>"
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
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "remark",
            "description": "<p>备注</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      }
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/activity/add.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/activity/del.do",
    "title": "删除活动风彩",
    "name": "delActivity",
    "group": "activity",
    "version": "1.0.0",
    "description": "<p>删除活动风彩</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>风采ID</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {}\n\t}",
          "type": "json"
        }
      ],
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
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      }
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/activity/del.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/activity/edit.do",
    "title": "编辑活动风彩",
    "name": "editActivity",
    "group": "activity",
    "version": "1.0.0",
    "description": "<p>编辑活动风彩</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>风采ID</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "images",
            "description": "<p>图片</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 用户ID\n\t\t\t\tsynopsis 描述\n\t\t\t\tcontent 内容\n\t\t\t\timages 图片\n\t\t\t\thitNum 点赞数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
          "type": "json"
        }
      ],
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "synopsis",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "images",
            "description": "<p>图片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "hitNum",
            "description": "<p>点赞数</p>"
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
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "remark",
            "description": "<p>备注</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      }
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/activity/edit.do"
      }
    ]
  },
  {
    "type": "GET",
    "url": "/activity/getId.do",
    "title": "获取活动风彩",
    "name": "getActivity",
    "group": "activity",
    "version": "1.0.0",
    "description": "<p>获取活动风彩</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>风采ID</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 用户ID\n\t\t\t\tsynopsis 描述\n\t\t\t\tcontent 内容\n\t\t\t\timages 图片\n\t\t\t\thitNum 点赞数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
          "type": "json"
        }
      ],
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "synopsis",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "images",
            "description": "<p>图片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "hitNum",
            "description": "<p>点赞数</p>"
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
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "remark",
            "description": "<p>备注</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      }
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/activity/getId.do"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
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
    "type": "GET",
    "url": "/supplier/getId.do",
    "title": "获取商家管理",
    "name": "getSupplier",
    "group": "supplier",
    "version": "1.0.0",
    "description": "<p>获取商家管理</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>商家管理ID</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tsupplierName 供应商名\n\t\t\t\tsynopsis 简介\n\t\t\t\tcontent 详情\n\t\t\t\tlinkValue 链接\n\t\t\t\tlistImages 小图片\n\t\t\t\tbannerImages banner图\n\t\t\t\tsupplierAddress 地址\n\t\t\t\ttelPhone 电话\n\t\t\t\tlinkMan 联系人\n\t\t\t\tsupplierType 类型\n\t\t\t\tgrade 评分\n\t\t\t\taverage 人均\n\t\t\t\tlongitude 经度\n\t\t\t\tlatitude 纬度\n\t\t\t\thitNum 点击数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态(0:删除  1:正常)\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
          "type": "json"
        }
      ],
      "fields": {
        "Success 200": [
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
            "field": "supplierName",
            "description": "<p>供应商名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "synopsis",
            "description": "<p>简介</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "content",
            "description": "<p>详情</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "linkValue",
            "description": "<p>链接</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "listImages",
            "description": "<p>小图片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bannerImages",
            "description": "<p>banner图</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierAddress",
            "description": "<p>地址</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "telPhone",
            "description": "<p>电话</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "linkMan",
            "description": "<p>联系人</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "supplierType",
            "description": "<p>类型</p>"
          },
          {
            "group": "Success 200",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "grade",
            "description": "<p>评分</p>"
          },
          {
            "group": "Success 200",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "average",
            "description": "<p>人均</p>"
          },
          {
            "group": "Success 200",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "longitude",
            "description": "<p>经度</p>"
          },
          {
            "group": "Success 200",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "latitude",
            "description": "<p>纬度</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "hitNum",
            "description": "<p>点击数</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      }
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/supplier/SupplierController.java",
    "groupTitle": "supplier",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/supplier/getId.do"
      }
    ]
  },
  {
    "type": "GET",
    "url": "/supplier/index.do",
    "title": "商家管理列表",
    "name": "supplierList",
    "group": "supplier",
    "version": "1.0.0",
    "description": "<p>商家管理列表</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tsupplierName 供应商名\n\t\t\t\tsynopsis 简介\n\t\t\t\tcontent 详情\n\t\t\t\tlinkValue 链接\n\t\t\t\tlistImages 小图片\n\t\t\t\tbannerImages banner图\n\t\t\t\tsupplierAddress 地址\n\t\t\t\ttelPhone 电话\n\t\t\t\tlinkMan 联系人\n\t\t\t\tsupplierType 类型\n\t\t\t\tgrade 评分\n\t\t\t\taverage 人均\n\t\t\t\tlongitude 经度\n\t\t\t\tlatitude 纬度\n\t\t\t\thitNum 点击数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态(0:删除  1:正常)\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
          "type": "json"
        }
      ],
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "synopsis",
            "description": "<p>描述</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "content",
            "description": "<p>内容</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "images",
            "description": "<p>图片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "hitNum",
            "description": "<p>点赞数</p>"
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
            "description": "<p>状态</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "remark",
            "description": "<p>备注</p>"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      }
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/supplier/SupplierController.java",
    "groupTitle": "supplier",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/supplier/index.do"
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
  },
  {
    "type": "POST",
    "url": "/user/alterpass.do",
    "title": "修改登录密码",
    "name": "alterpass",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>修改登录密码</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userPassword",
            "description": "<p>用户需修改的密码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>注销成功</p>"
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
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/user/alterpass.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/user/login.do",
    "title": "用户登录",
    "name": "login",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>用户登录</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userName",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "password",
            "description": "<p>用户密码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
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
            "field": "token",
            "description": "<p>用户token</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "certification",
            "description": "<p>真名</p>"
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
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n\t}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/user/login.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/user/logout.do",
    "title": "用户注销",
    "name": "logout",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>用户注销</p>",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>注销成功</p>"
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
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/user/logout.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/user/reg.do",
    "title": "用户注册",
    "name": "reg",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>用户注册</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userName",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userPassword",
            "description": "<p>登录密码</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "refereeId",
            "description": "<p>用户推荐人，填写用户手机号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "twoPassword",
            "description": "<p>支付密码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
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
            "field": "token",
            "description": "<p>用户token</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "certification",
            "description": "<p>真名</p>"
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
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n\t}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/user/reg.do"
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
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
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
  },
  {
    "type": "POST",
    "url": "/user/updPayPass.do",
    "title": "修改支付密码",
    "name": "updPayPass",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>修改支付密码</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "twoPassword",
            "description": "<p>用户需修改的密码</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>注销成功</p>"
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
            "type": "String",
            "optional": false,
            "field": "data",
            "description": "<p>返回成功的数据</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{}",
          "type": "json"
        }
      ]
    },
    "filename": "D:/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://127.0.0.1:8080/user/updPayPass.do"
      }
    ]
  }
] });
