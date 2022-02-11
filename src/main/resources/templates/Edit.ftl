<html layout:decorator="public/form" xmlns:miguo="http://www.w3.org/1999/html">
<div layout:fragment="content">
	<form class="layui-form model-form" action="" <#if is_split==true>style="width:80%;"</#if>>
		<input name="id" type="hidden" th:value="${tplTag}{info['id']}?:0">
		<#if hasPid = true>
		<input name="pid" id="pid" type="hidden" th:value="${tplTag}{info['pid']}?:0">
		</#if>
<#if model_column?exists>
	<#list model_column as model>
	<#if is_split==false>
	<#if model.changeColumnName?uncap_first != "createUser" && model.changeColumnName?uncap_first != "createTime" && model.changeColumnName?uncap_first != "updateUser" && model.changeColumnName?uncap_first != "updateTime" && model.changeColumnName?uncap_first != "mark">
		<#if (model.columnType = 'VARCHAR' || model.columnType = 'CHAR' || model.columnType = 'TEXT' || model.columnType = 'MEDIUMTEXT')>
		<#if model.columnImage == true>
		<div class="layui-form-item">
			<label class="layui-form-label">${model.columnComment}：</label>
			<widget:uploadSingleImage name="${model.changeColumnName?uncap_first}|${model.columnComment}|90x90|${entityName?lower_case}|建议上传尺寸450x450" th:value="${tplTag}{info['${model.changeColumnName?uncap_first}']}"/>
		</div>
		<#elseif model.columnTextArea == true>
		<div class="layui-form-item">
			<label class="layui-form-label">${model.columnComment}：</label>
			<div class="layui-input-block">
				<textarea name="${model.changeColumnName?uncap_first}" placeholder="请输入${model.columnComment}" class="layui-textarea" th:text="${tplTag}{info['${model.changeColumnName?uncap_first}']}"></textarea>
				<#if model.columnType = 'TEXT'>
				<widget:kindEditor name="${model.changeColumnName?uncap_first}" type="default" dirname="${entityName?lower_case}" width="100%" height="350"/>
				</#if>
			</div>
		</div>
		<#elseif model.changeColumnName?uncap_first = "mobile">
		<div class="layui-form-item">
			<label class="layui-form-label">手机号码：</label>
			<div class="layui-input-block">
				<input name="mobile" th:value="${tplTag}{info['mobile']}" lay-verify="required|phone" placeholder="请输入手机号码" autocomplete="off" class="layui-input" type="tel">
			</div>
		</div>
		<#elseif model.changeColumnName?uncap_first = "email">
		<div class="layui-form-item">
			<label class="layui-form-label">电子邮箱：</label>
			<div class="layui-input-block">
				<input name="email" th:value="${tplTag}{info['email']}" lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off" class="layui-input" type="text">
			</div>
		</div>
		<#elseif model.changeColumnName?uncap_first = "identity">
		<div class="layui-form-item">
			<label class="layui-form-label">身份证号：</label>
			<div class="layui-input-block">
				<input name="identity" th:value="${tplTag}{info['identity']}" lay-verify="identity" placeholder="请输入身份证号" autocomplete="off" class="layui-input" type="text">
			</div>
		</div>
		<#elseif model.hasColumnCommentValue = true>
		<div class="layui-inline">
			<label class="layui-form-label">${model.columnCommentName}：</label>
			<div class="layui-input-inline">
				<widget:singleSelect name="${model.changeColumnName?uncap_first}|1|${model.columnCommentName}|name|id" data="${model.columnValue}" th:value="${tplTag}{info['${model.changeColumnName?uncap_first}']}?:${model.columnDefaultValue}"/>
			</div>
		</div>
		<#else>
		<div class="layui-form-item">
			<label class="layui-form-label">${model.columnComment}：</label>
			<div class="layui-input-block">
				<input name="${model.changeColumnName?uncap_first}" th:value="${tplTag}{info['${model.changeColumnName?uncap_first}']}" lay-verify="required" autocomplete="off" placeholder="请输入${model.columnComment}" class="layui-input" type="text">
			</div>
		</div>
		</#if>
		</#if>
		<#if (model.columnType = 'DATETIME' || model.columnType = 'DATE' || model.columnType = 'TIME' || model.columnType = 'YEAR' || model.columnType = 'TIMESTAMP') >
		<div class="layui-form-item">
			<label class="layui-form-label">${model.columnComment}：</label>
			<div class="layui-input-block">
				<widget:dateSelect name="${model.changeColumnName?uncap_first}|${model.columnComment}|${model.columnType?lower_case}" th:value="${tplTag}{info['${model.changeColumnName?uncap_first}']}"/>
			</div>
		</div>
		</#if>
		<#if (model.columnType = 'TINYINT UNSIGNED' || model.columnType = 'TINYINT')>
		<#if model.columnSwitch == true>
		<div class="layui-form-item">
			<label class="layui-form-label">${model.columnCommentName}：</label>
			<div class="layui-input-block">
				<widget:switchCheck name="${model.changeColumnName?uncap_first}" data="${model.columnSwitchValue}" th:value="${tplTag}{info['${model.changeColumnName?uncap_first}']} ?: ${model.columnDefaultValue}"/>
			</div>
		</div>
		<#else>
		<div class="layui-form-item">
			<label class="layui-form-label">${model.columnCommentName}：</label>
			<div class="layui-input-block">
				<widget:singleSelect name="${model.changeColumnName?uncap_first}|1|${model.columnCommentName}|name|id" data="${model.columnValue}" th:value="${tplTag}{info['${model.changeColumnName?uncap_first}']}?:${model.columnDefaultValue}"/>
			</div>
		</div>
		</#if>
		</#if>
		<#if (model.columnType = 'INT UNSIGNED' || model.columnType = 'INT' || model.columnType = 'SMALLINT UNSIGNED' || model.columnType = 'SMALLINT' || model.columnType = 'BIGINT UNSIGNED' || model.columnType = 'BIGINT' || model.columnType = 'MEDIUMINT UNSIGNED' || model.columnType = 'MEDIUMINT')>
		<div class="layui-form-item">
			<label class="layui-form-label">${model.columnComment}：</label>
			<div class="layui-input-block">
				<input name="${model.changeColumnName?uncap_first}" th:value="${tplTag}{info['${model.changeColumnName?uncap_first}']}" lay-verify="required|number" autocomplete="off" placeholder="请输入${model.columnComment}" class="layui-input" type="text">
			</div>
		</div>
		</#if>
	</#if>
	<#else>
		<div class="layui-form-item">
		<#if model[0]?exists>
		<#if (model[0].columnType = 'VARCHAR' || model[0].columnType = 'CHAR' || model[0].columnType = 'TEXT' || model[0].columnType = 'MEDIUMTEXT')>
			<#if model[0].columnImage == true>
			<div class="layui-inline">
				<label class="layui-form-label">${model[0].columnComment}：</label>
				<widget:uploadSingleImage name="${model[0].changeColumnName?uncap_first}|${model[0].columnComment}|90x90|${entityName?lower_case}|建议上传尺寸450x450" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}"/>
			</div>
			<#elseif model[0].columnTextArea == true>
			<label class="layui-form-label">${model[0].columnComment}：</label>
			<div class="layui-input-block">
				<textarea name="${model[0].changeColumnName?uncap_first}" placeholder="请输入${model[0].columnComment}" class="layui-textarea" th:text="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}"></textarea>
			<#if model[0].columnType = 'TEXT'>
				<widget:kindEditor name="${model[0].changeColumnName?uncap_first}" type="default" dirname="${entityName?lower_case}" width="100%" height="350"/>
			</#if>
			</div>
			<#elseif model[0].changeColumnName?uncap_first = "mobile">
			<div class="layui-inline">
				<label class="layui-form-label">手机号码：</label>
				<div class="layui-input-inline">
					<input name="mobile" th:value="${tplTag}{info['mobile']}" lay-verify="required|phone" placeholder="请输入手机号码" autocomplete="off" class="layui-input" type="tel">
				</div>
			</div>
			<#elseif model[0].changeColumnName?uncap_first = "email">
			<div class="layui-inline">
				<label class="layui-form-label">电子邮箱：</label>
				<div class="layui-input-inline">
					<input name="email" th:value="${tplTag}{info['email']}" lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off" class="layui-input" type="text">
				</div>
			</div>
			<#elseif model[0].changeColumnName?uncap_first = "identity">
			<div class="layui-inline">
				<label class="layui-form-label">身份证号：</label>
				<div class="layui-input-inline">
					<input name="identity" th:value="${tplTag}{info['identity']}" lay-verify="identity" placeholder="请输入身份证号" autocomplete="off" class="layui-input" type="text">
				</div>
			</div>
			<#elseif model[0].hasColumnCommentValue = true>
				<#if model[1].columnType = 'VARCHAR'>
				<div class="layui-inline">
					<label class="layui-form-label">${model[0].columnCommentName}：</label>
					<div class="layui-input-inline">
						<widget:checkboxSingleSelect name="${model[0].changeColumnName?uncap_first}|name|id" data="${model[0].columnValue}" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}"/>
					</div>
				</div>
				<#else>
				<div class="layui-inline">
					<label class="layui-form-label">${model[0].columnCommentName}：</label>
					<div class="layui-input-inline">
						<widget:singleSelect name="${model[0].changeColumnName?uncap_first}|1|${model[0].columnCommentName}|name|id" data="${model[0].columnValue}" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}?:${model[0].columnDefaultValue}"/>
					</div>
				</div>
				</#if>
			<#else>
			<div class="layui-inline">
				<label class="layui-form-label">${model[0].columnComment}：</label>
				<div class="layui-input-inline">
					<input name="${model[0].changeColumnName?uncap_first}" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}" lay-verify="required" autocomplete="off" placeholder="请输入${model[0].columnComment}" class="layui-input" type="text">
				</div>
			</div>
			</#if>
		</#if>
		<#if (model[0].columnType = 'DATETIME' || model[0].columnType = 'DATE' || model[0].columnType = 'TIME' || model[0].columnType = 'YEAR' || model[0].columnType = 'TIMESTAMP') >
			<div class="layui-inline">
				<label class="layui-form-label">${model[0].columnComment}：</label>
				<div class="layui-input-inline">
					<widget:dateSelect name="${model[0].changeColumnName?uncap_first}|${model[0].columnComment}|${model[0].columnType?lower_case}" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}"/>
				</div>
			</div>
		</#if>
		<#if (model[0].columnType = 'TINYINT UNSIGNED' || model[0].columnType = 'TINYINT')>
		<#if model[0].columnSwitch == true>
			<div class="layui-inline">
				<label class="layui-form-label">${model[0].columnCommentName}：</label>
				<div class="layui-input-inline">
					<widget:switchCheck name="${model[0].changeColumnName?uncap_first}" data="${model[0].columnSwitchValue}" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']} ?: ${model[0].columnDefaultValue}"/>
				</div>
			</div>
		<#else>
			<div class="layui-inline">
				<label class="layui-form-label">${model[0].columnCommentName}：</label>
				<div class="layui-input-inline">
					<widget:singleSelect name="${model[0].changeColumnName?uncap_first}|1|${model[0].columnCommentName}|name|id" data="${model[0].columnValue}" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}?:${model[0].columnDefaultValue}"/>
				</div>
			</div>
		</#if>
		</#if>
		<#if (model[0].columnType = 'INT UNSIGNED' || model[0].columnType = 'INT' || model[0].columnType = 'SMALLINT UNSIGNED' || model[0].columnType = 'SMALLINT' || model[0].columnType = 'BIGINT UNSIGNED' || model[0].columnType = 'BIGINT' || model[0].columnType = 'MEDIUMINT UNSIGNED' || model[0].columnType = 'MEDIUMINT')>
			<div class="layui-inline">
				<label class="layui-form-label">${model[0].columnComment}：</label>
				<div class="layui-input-inline">
					<input name="${model[0].changeColumnName?uncap_first}" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}" lay-verify="required|number" autocomplete="off" placeholder="请输入${model[0].columnComment}" class="layui-input" type="text">
				</div>
			</div>
		</#if>
		<#if (model[0].columnType = 'DECIMAL UNSIGNED' || model[0].columnType = 'DECIMAL')>
			<div class="layui-inline">
				<label class="layui-form-label">${model[0].columnComment}：</label>
				<div class="layui-input-inline">
					<input name="${model[0].changeColumnName?uncap_first}" th:value="${tplTag}{info['${model[0].changeColumnName?uncap_first}']}" lay-verify="required" autocomplete="off" placeholder="请输入${model[0].columnComment}" class="layui-input" type="text">
				</div>
			</div>
		</#if>
		</#if>
		<#if model[1]?exists>
		<#if (model[1].columnType = 'VARCHAR' || model[1].columnType = 'CHAR' || model[1].columnType = 'TEXT' || model[1].columnType = 'MEDIUMTEXT')>
		<#if model[1].columnImage == true>
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnComment}：</label>
				<widget:uploadSingleImage name="${model[1].changeColumnName?uncap_first}|${model[1].columnComment}|90x90|temp|建议上传尺寸450x450" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']}"/>
			</div>
		<#elseif model[1].changeColumnName?uncap_first = "mobile">
			<div class="layui-inline">
				<label class="layui-form-label">手机号码：</label>
				<div class="layui-input-inline">
					<input name="mobile" th:value="${tplTag}{info['mobile']}" lay-verify="required|phone" placeholder="请输入手机号码" autocomplete="off" class="layui-input" type="tel">
				</div>
			</div>
		<#elseif model[1].changeColumnName?uncap_first = "email">
			<div class="layui-inline">
				<label class="layui-form-label">电子邮箱：</label>
				<div class="layui-input-inline">
					<input name="email" th:value="${tplTag}{info['email']}" lay-verify="required|email" placeholder="请输入邮箱" autocomplete="off" class="layui-input" type="text">
				</div>
			</div>
		<#elseif model[1].changeColumnName?uncap_first = "identity">
			<div class="layui-inline">
				<label class="layui-form-label">身份证号：</label>
				<div class="layui-input-inline">
					<input name="identity" th:value="${tplTag}{info['identity']}" lay-verify="identity" placeholder="请输入身份证号" autocomplete="off" class="layui-input" type="text">
				</div>
			</div>
		<#elseif model[1].hasColumnCommentValue = true>
			<#if model[1].columnType = 'VARCHAR'>
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnCommentName}：</label>
				<div class="layui-input-inline">
					<widget:checkboxSingleSelect name="${model[1].changeColumnName?uncap_first}|name|id" data="${model[1].columnValue}" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']}"/>
				</div>
			</div>
			<#else>
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnCommentName}：</label>
				<div class="layui-input-inline">
					<widget:singleSelect name="${model[1].changeColumnName?uncap_first}|1|${model[1].columnCommentName}|name|id" data="${model[1].columnValue}" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']}?:${model[1].columnDefaultValue}"/>
				</div>
			</div>
			</#if>
		<#else>
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnComment}：</label>
				<div class="layui-input-inline">
					<input name="${model[1].changeColumnName?uncap_first}" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']}" lay-verify="required" autocomplete="off" placeholder="请输入${model[1].columnComment}" class="layui-input" type="text">
				</div>
			</div>
		</#if>
		</#if>
		<#if (model[1].columnType = 'DATETIME' || model[1].columnType = 'DATE' || model[1].columnType = 'TIME' || model[1].columnType = 'YEAR' || model[1].columnType = 'TIMESTAMP') >
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnComment}：</label>
				<div class="layui-input-inline">
					<widget:dateSelect name="${model[1].changeColumnName?uncap_first}|${model[1].columnComment}|${model[1].columnType?lower_case}" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']}"/>
				</div>
			</div>
		</#if>
		<#if (model[1].columnType = 'TINYINT UNSIGNED' || model[1].columnType = 'TINYINT')>
		<#if model[1].columnSwitch == true>
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnCommentName}：</label>
				<div class="layui-input-inline">
					<widget:switchCheck name="${model[1].changeColumnName?uncap_first}" data="${model[1].columnSwitchValue}" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']} ?: ${model[1].columnDefaultValue}"/>
				</div>
			</div>
		<#else>
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnCommentName}：</label>
				<div class="layui-input-inline">
					<widget:singleSelect name="${model[1].changeColumnName?uncap_first}|1|${model[1].columnCommentName}|name|id" data="${model[1].columnValue}" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']}?:${model[1].columnDefaultValue}"/>
				</div>
			</div>
		</#if>
		</#if>
		<#if (model[1].columnType = 'INT UNSIGNED' || model[1].columnType = 'INT' || model[1].columnType = 'SMALLINT UNSIGNED' || model[1].columnType = 'SMALLINT' || model[1].columnType = 'BIGINT UNSIGNED' || model[1].columnType = 'BIGINT' || model[1].columnType = 'MEDIUMINT UNSIGNED' || model[1].columnType = 'MEDIUMINT')>
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnComment}：</label>
				<div class="layui-input-inline">
					<input name="${model[1].changeColumnName?uncap_first}" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']}" lay-verify="required|number" autocomplete="off" placeholder="请输入${model[1].columnComment}" class="layui-input" type="text">
				</div>
			</div>
		</#if>
		<#if (model[1].columnType = 'DECIMAL UNSIGNED' || model[1].columnType = 'DECIMAL')>
			<div class="layui-inline">
				<label class="layui-form-label">${model[1].columnComment}：</label>
				<div class="layui-input-inline">
					<input name="${model[1].changeColumnName?uncap_first}" th:value="${tplTag}{info['${model[1].changeColumnName?uncap_first}']}" lay-verify="required" autocomplete="off" placeholder="请输入${model[1].columnComment}" class="layui-input" type="text">
				</div>
			</div>
		</#if>
		</#if>
		</div>
	</#if>
	</#list>
</#if>
		<widget:btnSubmit name="submit|立即保存,close|关闭"/>
	</form>
</div>
</html>