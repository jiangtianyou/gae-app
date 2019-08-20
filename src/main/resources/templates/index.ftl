<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SQL转Java JPA、MYBATIS实现类代码生成平台</title>
    <meta name="keywords" content="sql转实体类,sql转DAO,SQL转service,SQL转JPA实现,SQL转MYBATIS实现">
    <#import "common/common-import.ftl" as netCommon>
    <@netCommon.commonStyle />

    <@netCommon.commonScript />
    <script>
        var ddlSqlArea
        $(function () {

            /**
             * 初始化 table sql 3
             */
            ddlSqlArea = CodeMirror.fromTextArea(document.getElementById("ddlSqlArea"), {
                lineNumbers: true,
                matchBrackets: true,
                mode: "text/x-sql",
                lineWrapping: false,
                readOnly: false,
                foldGutter: true,
                gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
            });
            ddlSqlArea.setSize('auto', 'auto');
            // controller_ide
            var genCodeArea = CodeMirror.fromTextArea(document.getElementById("genCodeArea"), {
                lineNumbers: true,
                matchBrackets: true,
                mode: "text/x-java",
                lineWrapping: true,
                readOnly: true,
                foldGutter: true,
                gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
            });
            genCodeArea.setSize('auto', 'auto');

            var codeData;

            /**
             * 生成代码
             */
            $('#btnGenCode').click(function () {

                var tableSql = ddlSqlArea.getValue();
                $.ajax({
                    type: 'GET',
                    url: base_url + "/genCode",
                    data: {
                        "tableSql": tableSql,
                        "packageName": $("#packageName").val(),
                        "isJoin": $("#isJoin").val(),
                        "hasApi": $("#hasApi").val()
                    },
                    dataType: "json",
                    success: function (data) {
                        console.log('返回的数据-->',data)
                        if (data.code == 200) {
                            layer.open({
                                icon: '1',
                                content: "代码生成成功",
                                time: 500,
                                anim: 1,
                                end: function () {
                                    codeData = data.data;
                                    genCodeArea.setValue(codeData.mybatis);
                                    genCodeArea.setSize('auto', 'auto');
                                }
                            });
                        } else {
                            layer.open({
                                icon: '2',
                                content: (data.msg || '代码生成失败')
                            });
                        }
                    }
                });
            });


            /**
             * 按钮事件组
             */
            $('.generator').bind('click', function () {
                if (!$.isEmptyObject(codeData)) {
                    var id = this.id;
                    genCodeArea.setValue(codeData[id]);
                    genCodeArea.setSize('auto', 'auto');
                }
            });
        });
    </script>
</head>
<body style="background-color: #e9ecef">
<div class="container" style="padding-top: 30px;">
    <div class="container">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">包名路径</span>
            </div>
            <input type="text" class="form-control" id="packageName" name="packageName"
                   value="com.wdit.modules.xxx">
            <div class="input-group-prepend">
                <span class="input-group-text">是否含一对多关联</span>
            </div>
            <input type="text" class="form-control" id="isJoin" name="isJoin"
                               value="NO">
        </div>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Controller是否包含APi</span>
            </div>
            <input type="text" class="form-control" id="hasApi" name="hasApi" value="YES">
        </div>
        <textarea id="ddlSqlArea" placeholder="请输入表结构信息..." class="form-control btn-lg" style="height: 250px;">
CREATE TABLE `member` (
  `id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `nickname` varchar(10) COLLATE utf8_bin NOT NULL comment '昵称',
  `address` varchar(100) COLLATE utf8_bin NOT NULL comment '地址',
  `create_date` datetime DEFAULT NULL comment '创建时间',
  `update_date` datetime DEFAULT NULL comment '更新时间',
  `remarks` varchar(255) COLLATE utf8_bin DEFAULT NULL comment '备注',
  `del_flag` varchar(1) COLLATE utf8_bin DEFAULT '0' comment '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin_log comment='用户'
        </textarea><br>
        <p>
            <button class="btn btn-primary" id="btnGenCode">开始生成 »</button>
            <button id="download" class="btn btn-primary" style="margin-left: 20px">下载所有文件</button>

        </p>
        <hr>
        <div class="row" style="margin-top: 10px;">
            <div class="btn-toolbar col-md-5" role="toolbar" aria-label="Toolbar with button groups">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="btn btn-secondary disabled" id="btnGroupAddon">通用实体</div>
                    </div>
                </div>
                <div class="btn-group" role="group" aria-label="First group">
                    <button type="button" class="btn btn-default generator" id="model">entity(set/get)</button>
                    <button type="button" class="btn btn-default generator" id="converter">converter</button>
                </div>
            </div>
            <div class="btn-toolbar col-md-7" role="toolbar" aria-label="Toolbar with button groups">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="btn btn-secondary disabled" id="btnGroupAddon">Mybatis</div>
                    </div>
                </div>
                <div class="btn-group" role="group" aria-label="First group">
                    <button type="button" class="btn btn-default generator" id="mybatis">mybatis</button>
                    <button type="button" class="btn btn-default generator" id="mapper">mapper</button>
                    <button type="button" class="btn btn-default generator" id="service">service</button>
                    <button type="button" class="btn btn-default generator" id="controller">controller</button>
                    <button type="button" class="btn btn-default generator" id="api">controller-api</button>
                </div>
            </div>
        </div>


        <div class="row" style="margin-top: 10px;">
            <div class="btn-toolbar col-md-5" role="toolbar" aria-label="Toolbar with button groups">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="btn btn-secondary disabled" id="btnGroupAddon">VO</div>
                    </div>
                </div>
                <div class="btn-group" role="group" aria-label="First group">
                    <button type="button" class="btn btn-default generator" id="vo">
                        vo(详情)
                    </button>
                </div>
                <div class="btn-group" role="group" aria-label="First group">
                    <button type="button" class="btn btn-default generator" id="listVo">
                        listVo(列表)
                    </button>
                </div>
            </div>
        </div>
        <hr>
        <p>
            <button id="copyResult" class="btn btn-primary">复制结果</button>
        </p>
        <textarea id="genCodeArea" class="form-control btn-lg"></textarea>
        <script>
            var cb = new ClipboardJS('#copyResult', {
                text: function (trigger) {
                    return getCodeMirrorJQuery('#genCodeArea').getDoc().getValue();
                }
            });
            cb.on('success', function (e) {
                layer.msg('复制成功', {icon: 1});
            });

            // Retrieve a CodeMirror Instance via jQuery.
            function getCodeMirrorJQuery(target) {
                var $target = target instanceof jQuery ? target : $(target);
                if ($target.length === 0) {
                    throw new Error('Element does not reference a CodeMirror instance.');
                }

                if (!$target.hasClass('CodeMirror')) {
                    if ($target.is('textarea')) {
                        $target = $target.next('.CodeMirror');
                    }
                }

                return $target.get(0).CodeMirror;
            }
        </script>
    </div>
</div>

<div class="container">

    <hr>
    <footer>
        <footer class="bd-footer text-muted" role="contentinfo">
            <div class="container">
                <p><a href="https://github.com/jiangtianyou/gae-app-code-genereator">gae-app-code-genereator</a></p>
            </div>
        </footer>
    </footer>
</div> <!-- /container -->

<form id="downloadForm" action="/download" method="get" >
    <input type="text" name="tableSql" value="" hidden>
    <input type="text" name="packageName" value="" hidden>
    <input type="text" name="isJoin" value="" hidden>
</form>
<script>
    // 下载文件
    $('#download').click(function () {
        $('#downloadForm > input[name=tableSql]').val(ddlSqlArea.getValue());
        $('#downloadForm > input[name=isJoin]').val($("#isJoin").val());
        $('#downloadForm > input[name=packageName]').val($("#packageName").val());
        $("#downloadForm").submit();
    });
</script>
</body>
</html>
