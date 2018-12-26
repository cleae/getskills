
selectLimit
===
SELECT * from questionbank  ORDER BY  RAND() LIMIT 10

select_isperience
===
 select * from member where openId=#openId#

getMemberByOpenId
===
*  通过openID获取用户的基本信息
 select nickname,avatar from member where openid=#openId#
 
selectLimit10
===
   SELECT * FROM questionbank WHERE style IN(
    SELECT id FROM question_style WHERE is_del!=1
   )
    ORDER BY RAND() limit 10
 
update_experience
===
 update member set is_experience=1 where openId=#openId#
 
getRandomQuestionByLevel
===
*
 SELECT * FROM questionbank WHERE level=#level# and style IN(
  SELECT id FROM question_style WHERE is_del!=1
 )
  ORDER BY RAND() limit 10
  
  
getFiftheenQuestionByLevel
===
*  获取15道随机题目
    SELECT * FROM questionbank WHERE level=#level# and style IN(
     SELECT id FROM question_style WHERE is_del!=1
    )
     ORDER BY RAND() limit 15 
     
getRandomQuestion
===
* 随机获取10个题目
    SELECT * FROM questionbank WHERE style IN(
        SELECT id FROM question_style WHERE is_del!=1
       )
        ORDER BY RAND() limit 10
        
selectLike
===
select * from questionbank 
@if(!isEmpty(nickname)){
where style like #'%'+nickname+'%'#
 @}if(isEmpty(style)){
  where style=#style#
 @}
 order by rand()limit 10

selectLevel
===
select level from member  where openid=#openid#




updateBalance
===
update member set balance=#balance# where openid=#openid#

updatelevel
===
update member set level=#level# where openid=#openid#

selectBalance
===
select balance from balance where openid=#openid#

selectaward
===
select award from award where type=#type# and member_openid=#openid#

insertaward
===
insert into award (member_openid,type,award)values(#member_openid#,#type#,#award#)

updateaward
===
update award set award=#award# where member_openid=#openid#

selectMaxLevel
===
select MAX(type) from award where member_openid=#openid#

selectSumAwardByOpenid
===
select * from award where member_openid=#openid#




 