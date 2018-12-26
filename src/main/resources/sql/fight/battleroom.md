insertOne
===
insert into BattleRoom 
 @if(!isEmpty(member1_name)){
     values(#member1_name#,#rightNumber1#,#useTime1#)
     @}
     @if(!isEmpty(member2_name)){
        values(#member2_name#,#rightNumber2#,#useTime2#)
        @}     

insert
===
insert into BattleRoom (member1_name,rightNumber1,useTime1,member2_name,rightNumber2,useTime2)values(#member1_name#,#rightNumber1#,#useTime1#,#member2_name#,#rightNumber2#,#useTime2#)

insert2
===
insert into BattleRoom values(#member2_name#,#rightNumber2#,#useTime2#) where member1_name=#member1_name#

select
===
select *from BattleRoom where member1_name=#member1_name#

getMaxRoomNumber
===
** 获取最大的房间号
  SELECT MAX(number) FROM battle_room

selectRoom
===
select * from battle_room where roomNumber=#roomNumber#

update
===
update battle_room set winner=#winner# where number=#number#