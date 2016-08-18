insert into cat_tipo_empleado(tipo_empleado, descripcion) values ('SYSADMIN', 'Administrador de Sistemas');

select * from cat_tipo_empleado;

insert into ctrl_empleado (id_cat_tipo_empleado, nombre_empledado, ap_paterno, ap_materno, sexo, fecha_nacimiento, fecha_insercion, fecha_actualizacion) 
		values (1, 'Miguel', 'Noriega', 'Ortiz', 'Masculino', '1990-11-24', now(), now());
        
select * from ctrl_empleado;

insert into ctrl_seg_perfil(nombre_perfil) values ('Administrador de Sistemas');

select * from ctrl_seg_perfil;

insert into ctrl_seg_usuario(id_empleado, id_perfil, fecha_insercion, fecha_actualizacion) values (1, 1, now(), now());

select * from ctrl_seg_usuario;
