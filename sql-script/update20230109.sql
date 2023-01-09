-- 为了解决员工号重复可以添加的问题。

-- 先通过这个语句，删除多余的数据
delete from ums_employee
-- select *  from ums_employee ue2
where id   in (
select min_id from (
select  min(id) min_id from ums_employee ue
group by employee_id having count(1) > 1
)t
)


-- 再通过这个语句，将empid修改为唯一索引
ALTER TABLE hammers_db.ums_employee ADD CONSTRAINT ums_employee_un UNIQUE KEY (employee_id);
