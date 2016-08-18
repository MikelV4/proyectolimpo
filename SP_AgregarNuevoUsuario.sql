USE `bd_olimpo`;

DROP procedure IF EXISTS agregarNuevoUsuario;

DELIMITER //
CREATE PROCEDURE agregarNuevoUsuario(
	IN pc_nombreUsuario varchar(250),
	IN pc_apellidoPaterno varchar(250),
	IN pc_apellidoMaterno varchar(250),
	IN pb_imagen mediumblob,
	IN pd_fechaInsercion datetime,
	IN pd_fechaActualizacion datetime,
	IN pi_autorizo int(11)
)
BEGIN
	DECLARE vc_ctrlUsuario INT(11) DEFAULT 0;
    
    INSERT INTO `ctrl_usuario`(
	`Nombre_usuario`,
	`Ap_Paterno_usuario`,
	`Ap_materno_usuario`,
	`Imagen_usuario`,
	`fecha_insercion`,
	`fecha_actualizacion`,
	`autorizo`
    ) values (
    pc_nombreUsuario,
    pc_apellidoPaterno,
    pc_apellidoMaterno,
    pb_imagen,
    pd_fechaInsercion,
    pd_fechaActualizacion,
    pi_autorizo
    );
    
	SELECT * from `ctrl_usuario`;
END//
DELIMITER ;

