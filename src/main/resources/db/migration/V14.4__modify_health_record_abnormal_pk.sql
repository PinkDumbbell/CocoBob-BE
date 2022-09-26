alter table health_record_abnormal modify health_record_id bigint null;
alter table health_record_abnormal drop primary key, add primary key (health_record_id, abnormal_id);