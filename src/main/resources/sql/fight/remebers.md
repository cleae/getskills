
queryRembers
===
* 根据玩家的openId与风格获取速记知识点
   SELECT content FROM remebers WHERE is_del!=1
    @if(!isEmpty(level)){
    AND level=#level#
    @} 
    and style IN( 
 	SELECT id FROM question_style WHERE is_del!=1
 	) ORDER BY RAND() limit 1