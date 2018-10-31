define({ "api": [
  {
    "type": "get",
    "url": "/commonIntf/sendMessage.do",
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/common/CommonIntf.java",
    "groupTitle": "Common",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/commonIntf/sendMessage.do"
      }
    ]
  },
  {
    "type": "post",
    "url": "/commonIntf/uploadImg.do",
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
            "type": "String",
            "optional": false,
            "field": "fileData",
            "description": "<p>文件数据流Base64文件编码字符串</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "fileName",
            "description": "<p>文件名</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/common/CommonIntf.java",
    "groupTitle": "Common",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/commonIntf/uploadImg.do"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/aboutUs/AboutUsController.java",
    "groupTitle": "aboutUs",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/aboutUs/index.do"
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
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>当前用户ID,用户登录ID,如果未登录为0</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "getUserId",
            "description": "<p>用户活动风采列表</p>"
          },
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
            "description": "<p>图片多张图片以&quot;,&quot;号相隔 例a.png,b.png</p>"
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
            "type": "java.lang.String",
            "optional": false,
            "field": "activityGood",
            "description": "<p>用户是否点赞 0：未点赞 1：已点赞</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userName",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "trueName",
            "description": "<p>真名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userFace",
            "description": "<p>用户头像</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/activity/index.do"
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
            "description": "<p>图片多张图片以&quot;,&quot;号相隔 例a.png,b.png</p>"
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
            "description": "<p>图片多张图片以&quot;,&quot;号相隔 例a.png,b.png</p>"
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
            "type": "java.lang.String",
            "optional": false,
            "field": "activityGood",
            "description": "<p>用户是否点赞 0：未点赞 1：已点赞</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userName",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "trueName",
            "description": "<p>真名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userFace",
            "description": "<p>用户头像</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/activity/add.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/activity/addGood.do",
    "title": "添加活动点赞",
    "name": "addGood",
    "group": "activity",
    "version": "1.0.0",
    "description": "<p>添加活动点赞</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    []\n\t  }\n\t}",
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
            "type": "java.lang.Integer",
            "optional": false,
            "field": "activityId",
            "description": "<p>风采ID</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/activity/addGood.do"
      }
    ],
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
            "type": "java.lang.Integer",
            "optional": false,
            "field": "activityId",
            "description": "<p>风采ID</p>"
          }
        ]
      }
    }
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/activity/del.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/activity/delGood.do",
    "title": "删除活动点赞",
    "name": "delGood",
    "group": "activity",
    "version": "1.0.0",
    "description": "<p>删除活动点赞</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 删除成功,\n\t\"data\": {}\n\t}",
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/activity/delGood.do"
      }
    ],
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
            "type": "java.lang.Integer",
            "optional": false,
            "field": "activityId",
            "description": "<p>风采ID</p>"
          }
        ]
      }
    }
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
            "description": "<p>图片多张图片以&quot;,&quot;号相隔 例a.png,b.png</p>"
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
            "type": "java.lang.String",
            "optional": false,
            "field": "activityGood",
            "description": "<p>用户是否点赞 0：未点赞 1：已点赞</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userName",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "trueName",
            "description": "<p>真名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userFace",
            "description": "<p>用户头像</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/activity/edit.do"
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
            "description": "<p>图片多张图片以&quot;,&quot;号相隔 例a.png,b.png</p>"
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
            "type": "java.lang.String",
            "optional": false,
            "field": "activityGood",
            "description": "<p>用户是否点赞 0：未点赞 1：已点赞</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userName",
            "description": "<p>用户名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "trueName",
            "description": "<p>真名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userFace",
            "description": "<p>用户头像</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/activity/ActivityController.java",
    "groupTitle": "activity",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/activity/getId.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/address/addAddress.do",
    "title": "添加收货地址",
    "name": "addAddress",
    "group": "address",
    "version": "1.0.0",
    "description": "<p>添加收货地址</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>当前用户ID,用户登录ID</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "addressId",
            "description": "<p>收货地址id，id为空是新记录 新增， 非空是已经存在的记录调用修改</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "userName",
            "description": "<p>收货人姓名</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "userPhone",
            "description": "<p>收货人手机</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "address",
            "description": "<p>收货人所在的：省-市-区</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "addressDetails",
            "description": "<p>收货地址详情</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserAddressController.java",
    "groupTitle": "",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/address/addAddress.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/address/delAddress.do",
    "title": "删除地址",
    "name": "delAddress",
    "group": "address",
    "version": "1.0.0",
    "description": "<p>删除地址</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "addressid",
            "description": "<p>地址主键ID</p>"
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
            "type": "java.lang.Integer",
            "optional": false,
            "field": "addressid",
            "description": "<p>地址id</p>"
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
            "field": "username",
            "description": "<p>收货姓名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userphone",
            "description": "<p>收货电话</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "address",
            "description": "<p>收货地址（省市区）</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "addressDetails",
            "description": "<p>收货地址详情</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserAddressController.java",
    "groupTitle": "",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/address/delAddress.do"
      }
    ]
  },
  {
    "type": "GET",
    "url": "/address/listAddress.do",
    "title": "用户收货地址列表",
    "name": "listAddress",
    "group": "address",
    "version": "1.0.0",
    "description": "<p>用户收货地址列表</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>当前用户ID,用户登录ID</p>"
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
            "type": "java.lang.Integer",
            "optional": false,
            "field": "addressid",
            "description": "<p>地址id</p>"
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
            "field": "username",
            "description": "<p>收货姓名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "userphone",
            "description": "<p>收货电话</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "address",
            "description": "<p>收货地址（省市区）</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "addressDetails",
            "description": "<p>收货地址详情</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserAddressController.java",
    "groupTitle": "",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/address/listAddress.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/agency/add.do",
    "title": "添加代理管理",
    "name": "addAgency",
    "group": "agency",
    "version": "1.0.0",
    "description": "<p>添加代理管理</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 用户ID\n\t\t\t\tuserName 姓名\n\t\t\t\tmobile 手机号码\n\t\t\t\tidCard 身份证\n\t\t\t\tbankNumber 银行卡\n\t\t\t\tbankType 银行卡类型\n\t\t\t\tidcardFront 身份证正面照\n\t\t\t\tidcardBack 身份证反面照\n\t\t\t\tprovince 省份\n\t\t\t\tcity 城市\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态(0:删除  1:正常 2:审核通过)\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "field": "userName",
            "description": "<p>姓名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idCard",
            "description": "<p>身份证</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankNumber",
            "description": "<p>银行卡</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankType",
            "description": "<p>银行卡类型</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardFront",
            "description": "<p>身份证正面照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardBack",
            "description": "<p>身份证反面照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "province",
            "description": "<p>省份</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>城市</p>"
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
            "description": "<p>状态(0:删除  1:正常 2:审核通过)</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/agency/AgencyController.java",
    "groupTitle": "agency",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/agency/add.do"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
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
            "field": "userName",
            "description": "<p>姓名</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "idCard",
            "description": "<p>身份证</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankNumber",
            "description": "<p>银行卡</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankType",
            "description": "<p>银行卡类型</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardFront",
            "description": "<p>身份证正面照</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardBack",
            "description": "<p>身份证反面照</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "province",
            "description": "<p>省份</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>城市</p>"
          }
        ]
      }
    }
  },
  {
    "name": "agencyList",
    "group": "agency",
    "version": "1.0.0",
    "description": "<p>代理管理列表</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 用户ID\n\t\t\t\tuserName 姓名\n\t\t\t\tmobile 手机号码\n\t\t\t\tidCard 身份证\n\t\t\t\tbankNumber 银行卡\n\t\t\t\tbankType 银行卡类型\n\t\t\t\tidcardFront 身份证正面照\n\t\t\t\tidcardBack 身份证反面照\n\t\t\t\tprovince 省份\n\t\t\t\tcity 城市\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态(0:删除  1:正常 2:审核通过)\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "field": "userName",
            "description": "<p>姓名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idCard",
            "description": "<p>身份证</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankNumber",
            "description": "<p>银行卡</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankType",
            "description": "<p>银行卡类型</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardFront",
            "description": "<p>身份证正面照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardBack",
            "description": "<p>身份证反面照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "province",
            "description": "<p>省份</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>城市</p>"
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
            "description": "<p>状态(0:删除  1:正常 2:审核通过)</p>"
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
    "type": "",
    "url": "",
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/agency/AgencyController.java",
    "groupTitle": "agency",
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
    "url": "/agency/del.do",
    "title": "删除代理管理",
    "name": "delAgency",
    "group": "agency",
    "version": "1.0.0",
    "description": "<p>删除代理管理</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 删除成功,\n\t\"data\": {}\n\t}",
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/agency/AgencyController.java",
    "groupTitle": "agency",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/agency/del.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/agency/edit.do",
    "title": "修改代理管理",
    "name": "editAgency",
    "group": "agency",
    "version": "1.0.0",
    "description": "<p>修改代理管理</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 用户ID\n\t\t\t\tuserName 姓名\n\t\t\t\tmobile 手机号码\n\t\t\t\tidCard 身份证\n\t\t\t\tbankNumber 银行卡\n\t\t\t\tbankType 银行卡类型\n\t\t\t\tidcardFront 身份证正面照\n\t\t\t\tidcardBack 身份证反面照\n\t\t\t\tprovince 省份\n\t\t\t\tcity 城市\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态(0:删除  1:正常 2:审核通过)\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "field": "userName",
            "description": "<p>姓名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idCard",
            "description": "<p>身份证</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankNumber",
            "description": "<p>银行卡</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankType",
            "description": "<p>银行卡类型</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardFront",
            "description": "<p>身份证正面照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardBack",
            "description": "<p>身份证反面照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "province",
            "description": "<p>省份</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>城市</p>"
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
            "description": "<p>状态(0:删除  1:正常 2:审核通过)</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/agency/AgencyController.java",
    "groupTitle": "agency",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/agency/edit.do"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>id</p>"
          },
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
            "field": "userName",
            "description": "<p>姓名</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "idCard",
            "description": "<p>身份证</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankNumber",
            "description": "<p>银行卡</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankType",
            "description": "<p>银行卡类型</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardFront",
            "description": "<p>身份证正面照</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardBack",
            "description": "<p>身份证反面照</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "province",
            "description": "<p>省份</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>城市</p>"
          }
        ]
      }
    }
  },
  {
    "type": "GET",
    "url": "/agency/getId.do",
    "title": "获取代理管理",
    "name": "getAgency",
    "group": "agency",
    "version": "1.0.0",
    "description": "<p>获取代理管理</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 用户ID\n\t\t\t\tuserName 姓名\n\t\t\t\tmobile 手机号码\n\t\t\t\tidCard 身份证\n\t\t\t\tbankNumber 银行卡\n\t\t\t\tbankType 银行卡类型\n\t\t\t\tidcardFront 身份证正面照\n\t\t\t\tidcardBack 身份证反面照\n\t\t\t\tprovince 省份\n\t\t\t\tcity 城市\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态(0:删除  1:正常 2:审核通过)\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "field": "userName",
            "description": "<p>姓名</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号码</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idCard",
            "description": "<p>身份证</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankNumber",
            "description": "<p>银行卡</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "bankType",
            "description": "<p>银行卡类型</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardFront",
            "description": "<p>身份证正面照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "idcardBack",
            "description": "<p>身份证反面照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "province",
            "description": "<p>省份</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>城市</p>"
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
            "description": "<p>状态(0:删除  1:正常 2:审核通过)</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/agency/AgencyController.java",
    "groupTitle": "agency",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/agency/getId.do"
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
            "description": "<p>banner详情外部url链接地址</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/banner/BannerController.java",
    "groupTitle": "banner",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/banner/index.do"
      }
    ]
  },
  {
    "type": "GET",
    "url": "/beatufyspot/index.do",
    "title": "景区查询列表",
    "name": "beatufyspotList",
    "group": "beatufyspot",
    "version": "1.0.0",
    "description": "<p>景区查询列表</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\turl 景区查询url\n\t\t\t\tchannel 景区来源\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "type": "String",
            "optional": false,
            "field": "url",
            "description": "<p>景区查询url</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "channel",
            "description": "<p>景区来源</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/beatufyspot/BeatufySpotController.java",
    "groupTitle": "beatufyspot",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/beatufyspot/index.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/feedBack/addFeedBack.do",
    "title": "用户反馈",
    "name": "addFeedBack",
    "group": "feedBack",
    "version": "1.0.0",
    "description": "<p>用户反馈</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>当前用户ID,</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "feedBackContent",
            "description": "<p>反馈内容</p>"
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
            "description": "<p>返回成功信息</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/action/feedback/FeedBackController.java",
    "groupTitle": "feedBack",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/feedBack/addFeedBack.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/mall/list.do",
    "title": "商城，商品列表",
    "name": "list",
    "group": "mall",
    "version": "1.0.0",
    "description": "<p>商品列表</p>",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "hotGoodsList",
            "description": "<p>爆款商品</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "hotGoodsList.goodsId",
            "description": "<p>商品id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "hotGoodsList.title",
            "description": "<p>商品标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "hotGoodsList.GoodsDesc",
            "description": "<p>商品的描述</p>"
          },
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "hotGoodsList.goodsImg",
            "description": "<p>商品图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "hotGoodsList.goodsBanner",
            "description": "<p>商品详情页面banner图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "hotGoodsList.goodsDetailImg",
            "description": "<p>商品详情图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "double",
            "optional": false,
            "field": "hotGoodsList.shopPrice",
            "description": "<p>商品价格</p>"
          },
          {
            "group": "Success 200",
            "type": "double",
            "optional": false,
            "field": "hotGoodsList.marketPrice",
            "description": "<p>商品优惠价格</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "hotGoodsList.saleCount",
            "description": "<p>已售数量</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "hotGoodsList.brandName",
            "description": "<p>商品品牌名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "hotGoodsList.cateName",
            "description": "<p>商品类别名称</p>"
          },
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "normalGoodsList",
            "description": "<p>正常商品</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "normalGoodsList.goodsId",
            "description": "<p>商品id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "normalGoodsList.title",
            "description": "<p>商品标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "normalGoodsList.GoodsDesc",
            "description": "<p>商品的描述</p>"
          },
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "normalGoodsList.goodsImg",
            "description": "<p>商品图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "normalGoodsList.goodsBanner",
            "description": "<p>商品详情页面banner图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "normalGoodsList.goodsDetailImg",
            "description": "<p>商品详情图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "double",
            "optional": false,
            "field": "normalGoodsList.shopPrice",
            "description": "<p>商品价格</p>"
          },
          {
            "group": "Success 200",
            "type": "double",
            "optional": false,
            "field": "normalGoodsList.marketPrice",
            "description": "<p>商品优惠价格</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "normalGoodsList.saleCount",
            "description": "<p>已售数量</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "normalGoodsList.brandName",
            "description": "<p>商品品牌名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "normalGoodsList.cateName",
            "description": "<p>商品类别名称</p>"
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
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\"hotGoodsList\":[\n\t\t      \"goodsId\": \"1\",\n\t\t      \"title\": \"鹿无忧\",\n\t\t      \"goodsDtails\": \"提神抗疲劳\",\n\t\t      \"goodsImg\": \n\t\t                {\n\t\t                 \"img1\":\"d:/sasd.jgp\",\n\t\t                 \"img2\":\"d:/sasd.jgp\",\n\t\t                  } ,\n\t\t      \"saleTime\": \"2018-8-6 10：19：56\",\n\t\t      \"shopPrice\":4999.00,\n\t\t    ],\n         \"normalGoodsList\":[\n\t\t      \"goodsId\": \"1\",\n\t\t      \"title\": \"鹿无忧\",\n\t\t      \"goodsDtails\": \"提神抗疲劳\",\n\t\t      \"goodsImg\": \n\t\t                {\n\t\t                 \"img1\":\"d:/sasd.jgp\",\n\t\t                 \"img2\":\"d:/sasd.jgp\",\n\t\t                  } ,\n\t\t      \"saleTime\": \"2018-8-6 10：19：56\",\n\t\t      \"shopPrice\":4999.00,\n\t\t    ],\n\t}",
          "type": "json"
        }
      ]
    },
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/mall/GoodsController.java",
    "groupTitle": "mall",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/mall/list.do"
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
    "url": "/mall/listTravelCard.do",
    "title": "加入我们购买旅游卡",
    "name": "listTravelCard",
    "group": "mall",
    "version": "1.0.0",
    "description": "<p>加入我们购买旅游卡</p>",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Object[]",
            "optional": false,
            "field": "travelCardList",
            "description": "<p>爆款商品</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "travelCardList.goodsId",
            "description": "<p>商品id</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "travelCardList.title",
            "description": "<p>商品标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "travelCardList.GoodsDesc",
            "description": "<p>商品的描述</p>"
          },
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "travelCardList.goodsImg",
            "description": "<p>商品图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "json",
            "optional": false,
            "field": "travelCardList.goodsBanner",
            "description": "<p>商品详情页面banner图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "travelCardList.goodsDetailImg",
            "description": "<p>商品详情图片地址</p>"
          },
          {
            "group": "Success 200",
            "type": "double",
            "optional": false,
            "field": "travelCardList.shopPrice",
            "description": "<p>商品价格</p>"
          },
          {
            "group": "Success 200",
            "type": "double",
            "optional": false,
            "field": "travelCardList.marketPrice",
            "description": "<p>商品优惠价格</p>"
          },
          {
            "group": "Success 200",
            "type": "int",
            "optional": false,
            "field": "travelCardList.saleCount",
            "description": "<p>已售数量</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "travelCardList.brandName",
            "description": "<p>商品品牌名称</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "travelCardList.cateName",
            "description": "<p>商品类别名称</p>"
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
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {[\n\t\t      {\"goodsId\": \"1\",\n\t\t      \"title\": \"鹿无忧\",\n\t\t      \"GoodsDesc\": \"提神抗疲劳\",\n\t\t      \"goodsImg\": \n\t\t                {\n\t\t                 \"img1\":\"d:/sasd.jgp\",\n\t\t                 \"img2\":\"d:/sasd.jgp\",\n\t\t                  } ,\n\t\t\t  \"goodsBanner\":{\n\t\t                 \"img1\":\"d:/sasd.jgp\",\n\t\t                 \"img2\":\"d:/sasd.jgp\",\n\t\t                  } ,\n           \"goodsDetailImg\":\"http://sasd.jgp\",\n\t\t      \"saleTime\": \"2018-8-6 10：19：56\",\n\t\t      \"shopPrice\":4999.00,\n\t\t\t  \"marketPrice\":4000.00,\n\t\t\t  \"saleCount\": 30}\n\t\t    ] }\n\t}",
          "type": "json"
        }
      ]
    },
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/mall/GoodsController.java",
    "groupTitle": "mall",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/mall/listTravelCard.do"
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
    "type": "GET",
    "url": "/news/list.do",
    "title": "新闻列表",
    "name": "index",
    "group": "news",
    "version": "1.0.0",
    "description": "<p>新闻列表</p>",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>新闻ID</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>标题</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "content",
            "description": "<p>新闻详情外部链接url</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "createDate",
            "description": "<p>日期</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "image",
            "description": "<p>图片</p>"
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
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {}\n\t}",
          "type": "json"
        }
      ]
    },
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/news/NewsController.java",
    "groupTitle": "news",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/news/list.do"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/action/notice/NoticeController.java",
    "groupTitle": "notice",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/notice/list.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/order/createOrder.do",
    "title": "生成预支付订单",
    "name": "createOrder",
    "group": "order",
    "version": "1.0.0",
    "description": "<p>生成预支付订单</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userId",
            "description": "<p>用户id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "orderType",
            "description": "<p>支付方式 1微信2支付宝3其他</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "addressId",
            "description": "<p>订单送货地址id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "orderId",
            "description": "<p>订单id</p>"
          },
          {
            "group": "Parameter",
            "type": "json",
            "optional": false,
            "field": "cart",
            "description": "<p>商品信息：qty商品数量；goodsId商品编号；price商品单价</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\t唤起支付json字符串\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/order/OrderController.java",
    "groupTitle": "order",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/order/createOrder.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/supplier/add.do",
    "title": "添加商家管理",
    "name": "addSupplier",
    "group": "supplier",
    "version": "1.0.0",
    "description": "<p>添加商家管理</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 空\n\t\t\t\tsupplierName 供应商名\n\t\t\t\tsynopsis 简介\n\t\t\t\tcontent 详情\n\t\t\t\tlinkValue 链接\n\t\t\t\tlistImages 小图片\n\t\t\t\tbannerImages banner图\n\t\t\t\tbusiImage 营业执照\n\t\t\t\tshopImage 门店照片\n\t\t\t\tcity 省市/城市\n\t\t\t\tsupplierAddress 详细地址\n\t\t\t\ttelPhone 电话\n\t\t\t\tlinkMan 联系人\n\t\t\t\tsupplierType 类型\n\t\t\t\tgrade 评分\n\t\t\t\taverage 人均\n\t\t\t\tlongitude 经度\n\t\t\t\tlatitude 纬度\n\t\t\t\thitNum 点击数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "description": "<p>空</p>"
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
            "field": "busiImage",
            "description": "<p>营业执照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "shopImage",
            "description": "<p>门店照片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>省市/城市</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierAddress",
            "description": "<p>详细地址</p>"
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
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "distance",
            "description": "<p>距离</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "status",
            "description": "<p>状态 0:删除 1：正常 2:审核通过</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/supplier/SupplierController.java",
    "groupTitle": "supplier",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/supplier/add.do"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>供应商</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>空</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierName",
            "description": "<p>供应商名</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "synopsis",
            "description": "<p>简介</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "busiImage",
            "description": "<p>营业执照</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "shopImage",
            "description": "<p>门店照片</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>省市/城市</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierAddress",
            "description": "<p>详细地址</p>"
          },
          {
            "group": "Parameter",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "longitude",
            "description": "<p>经度</p>"
          },
          {
            "group": "Parameter",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "latitude",
            "description": "<p>纬度</p>"
          }
        ]
      }
    }
  },
  {
    "type": "POST",
    "url": "/supplier/del.do",
    "title": "删除商家管理",
    "name": "delSupplier",
    "group": "supplier",
    "version": "1.0.0",
    "description": "<p>删除商家管理</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 删除成功,\n\t\"data\": {}\n\t}",
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/supplier/SupplierController.java",
    "groupTitle": "supplier",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/supplier/del.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/supplier/edit.do",
    "title": "修改商家管理",
    "name": "editSupplier",
    "group": "supplier",
    "version": "1.0.0",
    "description": "<p>修改商家管理</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 空\n\t\t\t\tsupplierName 供应商名\n\t\t\t\tsynopsis 简介\n\t\t\t\tcontent 详情\n\t\t\t\tlinkValue 链接\n\t\t\t\tlistImages 小图片\n\t\t\t\tbannerImages banner图\n\t\t\t\tbusiImage 营业执照\n\t\t\t\tshopImage 门店照片\n\t\t\t\tcity 省市/城市\n\t\t\t\tsupplierAddress 详细地址\n\t\t\t\ttelPhone 电话\n\t\t\t\tlinkMan 联系人\n\t\t\t\tsupplierType 类型\n\t\t\t\tgrade 评分\n\t\t\t\taverage 人均\n\t\t\t\tlongitude 经度\n\t\t\t\tlatitude 纬度\n\t\t\t\thitNum 点击数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "description": "<p>空</p>"
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
            "field": "busiImage",
            "description": "<p>营业执照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "shopImage",
            "description": "<p>门店照片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>省市/城市</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierAddress",
            "description": "<p>详细地址</p>"
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
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "distance",
            "description": "<p>距离</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "status",
            "description": "<p>状态 0:删除 1：正常 2:审核通过</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/supplier/SupplierController.java",
    "groupTitle": "supplier",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/supplier/edit.do"
      }
    ],
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "id",
            "description": "<p>供应商</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>空</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierName",
            "description": "<p>供应商名</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "synopsis",
            "description": "<p>简介</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "busiImage",
            "description": "<p>营业执照</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "shopImage",
            "description": "<p>门店照片</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>省市/城市</p>"
          },
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierAddress",
            "description": "<p>详细地址</p>"
          },
          {
            "group": "Parameter",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "longitude",
            "description": "<p>经度</p>"
          },
          {
            "group": "Parameter",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "latitude",
            "description": "<p>纬度</p>"
          }
        ]
      }
    }
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
            "field": "userId",
            "description": "<p>用户ID</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 空\n\t\t\t\tsupplierName 供应商名\n\t\t\t\tsynopsis 简介\n\t\t\t\tcontent 详情\n\t\t\t\tlinkValue 链接\n\t\t\t\tlistImages 小图片\n\t\t\t\tbannerImages banner图\n\t\t\t\tbusiImage 营业执照\n\t\t\t\tshopImage 门店照片\n\t\t\t\tcity 省市/城市\n\t\t\t\tsupplierAddress 详细地址\n\t\t\t\ttelPhone 电话\n\t\t\t\tlinkMan 联系人\n\t\t\t\tsupplierType 类型\n\t\t\t\tgrade 评分\n\t\t\t\taverage 人均\n\t\t\t\tlongitude 经度\n\t\t\t\tlatitude 纬度\n\t\t\t\thitNum 点击数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "description": "<p>空</p>"
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
            "field": "busiImage",
            "description": "<p>营业执照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "shopImage",
            "description": "<p>门店照片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>省市/城市</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierAddress",
            "description": "<p>详细地址</p>"
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
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "distance",
            "description": "<p>距离</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "status",
            "description": "<p>状态 0:删除 1：正常 2:审核通过</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/supplier/SupplierController.java",
    "groupTitle": "supplier",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/supplier/getId.do"
      }
    ]
  },
  {
    "name": "supplierList",
    "group": "supplier",
    "version": "1.0.0",
    "description": "<p>商家管理列表</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierName",
            "description": "<p>供应商名查询</p>"
          },
          {
            "group": "Parameter",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "longitude",
            "description": "<p>经度</p>"
          },
          {
            "group": "Parameter",
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "latitude",
            "description": "<p>纬度</p>"
          },
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
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response: ",
          "content": " HTTP/1.1 200 OK \n{\n \"code\": 0\n\t\"msg\": 返回成功,\n\t\"data\": {\n\t    [\n\t\t\t{\n\t\t\t\tid id\n\t\t\t\tuserId 空\n\t\t\t\tsupplierName 供应商名\n\t\t\t\tsynopsis 简介\n\t\t\t\tcontent 详情\n\t\t\t\tlinkValue 链接\n\t\t\t\tlistImages 小图片\n\t\t\t\tbannerImages banner图\n\t\t\t\tbusiImage 营业执照\n\t\t\t\tshopImage 门店照片\n\t\t\t\tcity 省市/城市\n\t\t\t\tsupplierAddress 详细地址\n\t\t\t\ttelPhone 电话\n\t\t\t\tlinkMan 联系人\n\t\t\t\tsupplierType 类型\n\t\t\t\tgrade 评分\n\t\t\t\taverage 人均\n\t\t\t\tlongitude 经度\n\t\t\t\tlatitude 纬度\n\t\t\t\thitNum 点击数\n\t\t\t\tcreateDate 创建时间\n\t\t\t\tcreateName 创建人\n\t\t\t\tmodifyDate 更新时间\n\t\t\t\tmodifyName 更新人\n\t\t\t\tstatus 状态\n\t\t\t\tremark 备注\n\t\t\t}\n\t\t]\n\t  }\n\t}",
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
            "description": "<p>空</p>"
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
            "field": "busiImage",
            "description": "<p>营业执照</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "shopImage",
            "description": "<p>门店照片</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "city",
            "description": "<p>省市/城市</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.String",
            "optional": false,
            "field": "supplierAddress",
            "description": "<p>详细地址</p>"
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
            "type": "java.math.BigDecimal",
            "optional": false,
            "field": "distance",
            "description": "<p>距离</p>"
          },
          {
            "group": "Success 200",
            "type": "java.lang.Integer",
            "optional": false,
            "field": "status",
            "description": "<p>状态 0:删除 1：正常 2:审核通过</p>"
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
    "type": "",
    "url": "",
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/supplier/SupplierController.java",
    "groupTitle": "supplier"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/alterpass.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/user/authentication.do",
    "title": "用户认证",
    "name": "authentication",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>用户认证</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Integer",
            "optional": false,
            "field": "userId",
            "description": "<p>用户ID</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "trueName",
            "description": "<p>真名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idnumber",
            "description": "<p>身份证号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sex",
            "description": "<p>性别 1男 2女</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "banknumber",
            "description": "<p>卡号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "banktype",
            "description": "<p>开户行</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idcardFront",
            "description": "<p>身份证正面像</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idcardBack",
            "description": "<p>身份证背面像</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "userImage",
            "description": "<p>用户图像</p>"
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
            "description": "<p>认证成功</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/authentication.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/user/getUserInfo.do",
    "title": "获取用户信息",
    "name": "getUserInfo",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>获取用户信息</p>",
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
            "type": "int",
            "optional": false,
            "field": "userLevel",
            "description": "<p>用户等级，默认为：0：普通用户；1：会员;2：VIP；3：城市合伙人；4：股东</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "trueName",
            "description": "<p>用户姓名</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "idnumber",
            "description": "<p>身份证号码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "sex",
            "description": "<p>默认为：0（无）；男为：1；女为：2</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "refereeid",
            "description": "<p>用户推荐人的手机号码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "banknumber",
            "description": "<p>银行卡卡号</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "banktype",
            "description": "<p>银行卡开户行</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "certification",
            "description": "<p>用户认证状态，默认为：0（未认证） 1:已实名认证</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "userImage",
            "description": "<p>用记头像</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "idcardFront",
            "description": "<p>用户身份证正面</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "idcardBack",
            "description": "<p>用户身份背面</p>"
          },
          {
            "group": "Success 200",
            "type": "Bigdecimal",
            "optional": false,
            "field": "amount",
            "description": "<p>现金用户</p>"
          },
          {
            "group": "Success 200",
            "type": "Bigdecimal",
            "optional": false,
            "field": "originalAmount",
            "description": "<p>抵用券</p>"
          },
          {
            "group": "Success 200",
            "type": "Bigdecimal",
            "optional": false,
            "field": "pointAmount",
            "description": "<p>积分</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/getUserInfo.do"
      }
    ]
  },
  {
    "type": "POST",
    "url": "/user/infoUser.do",
    "title": "修改用户",
    "name": "infoUser",
    "group": "user",
    "version": "1.0.0",
    "description": "<p>修改用户</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "trueName",
            "description": "<p>真名</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "mobile",
            "description": "<p>手机</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idnumber",
            "description": "<p>身份证号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "sex",
            "description": "<p>性别</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "banknumber",
            "description": "<p>卡号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "banktype",
            "description": "<p>开户行</p>"
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
            "description": "<p>修改成功</p>"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/infoUser.do"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/login.do"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/logout.do"
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
            "field": "refereeUserMobile",
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/reg.do"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/shareList.do"
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
    "filename": "E:/CODE/谭总-乐游环球/code/travel/dce-app/src/main/java/com/dce/business/actions/user/UserController.java",
    "groupTitle": "user",
    "sampleRequest": [
      {
        "url": "http://103.82.143.121:8080/dce-app/user/updPayPass.do"
      }
    ]
  }
] });
