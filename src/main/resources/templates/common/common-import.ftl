<#macro commonStyle>

<#-- favicon -->
<link rel="icon" href="favicon.ico" />

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 4 -->
    <link href="/public/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="/public/css/font-awesome.min.css" rel="stylesheet">
    <!-- Ionicons -->
    <link href="/public/css/ionicons.min.css" rel="stylesheet">

    <link href="/public/css/codemirror.min.css" rel="stylesheet">

<script>var base_url = '${request.contextPath}';</script>
</#macro>

<#macro commonScript>

    <!-- jQuery -->
    <script src="/public/js/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="/public/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="/public/js/fastclick.min.js"></script>
    <script src="/public/js/jquery.slimscroll.min.js"></script>
    <script src="//cdn.bootcss.com/layer/2.3/layer.js"></script>
    <script src="/public/js/codemirror.min.js"></script>
    <script src="/public/js/placeholder.min.js"></script>
    <script src="/public/js/clike.min.js"></script>
    <script src="/public/js/sql.min.js"></script>
    <script src="/public/js/xml.min.js"></script>
    <script src="/public/js/clipboard.min.js"></script>
</#macro>


<#macro commonFooter >
<footer class="main-footer">
    <div class="container">
        Powered by <b>Spring Boot Code Generator</b> base on XXL Code Generator
        <div class="pull-right hidden-xs">
            <strong>Copyright &copy; 2018-${.now?string('yyyy')} &nbsp;
                <a href="https://github.com/moshowgame/SpringBootCodeGenerator" target="_blank" >SpringBootCodeGenerator</a>
                <a href="https://github.com/xuxueli/xxl-code-generator" target="_blank" >xxl-code-generator</a>
            </strong><!-- All rights reserved. -->
        </div>
    </div>
</footer>
</#macro>