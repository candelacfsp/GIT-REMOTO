-- Function: holamundo(integer)

-- DROP FUNCTION holamundo(integer);

--CatalogoSP--------
CREATE OR REPLACE FUNCTION altatomo(integer, character varying,boolean, integer)
  RETURNS void AS
'storedProcedures.CatalogoSP.altaTomo'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION altatomo(integer, character varying,boolean, integer) OWNER TO postgres;

CREATE OR REPLACE FUNCTION bajatomo(integer)
  RETURNS void AS
'storedProcedures.CatalogoSP.bajaTomo'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION bajatomo(integer) OWNER TO postgres;



CREATE OR REPLACE FUNCTION bajatomo(integer)
  RETURNS void AS
'storedProcedures.CatalogoSP.bajaTomo'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION bajatomo(integer) OWNER TO postgres;

--DetallePedidoPersonalSP


CREATE OR REPLACE FUNCTION creardetalle(integer,integer,float8,integer)
  RETURNS void AS
'storedProcedures.DetallePedidoPersonalSP.crearDetalle'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION creardetalle(integer,integer,float8,integer) OWNER TO postgres;

--FacturaPersonalSP
CREATE OR REPLACE FUNCTION crearfactura(Date,integer,integer,integer,boolean,integer)
  RETURNS void AS
'storedProcedures.FacturaPersonalSP.crearFactura'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION crearfactura(Date,integer,integer,integer,boolean,integer) OWNER TO postgres;

CREATE OR REPLACE FUNCTION almacenar_factimpaga(integer)
  RETURNS void AS
'storedProcedures.FacturaPersonalSP.almacenar_factImpaga'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION almacenar_factimpaga(integer) OWNER TO postgres;



---PedidoFabricaSP
CREATE OR REPLACE FUNCTION marcarpedidosp(integer,Date)
  RETURNS void AS
'storedProcedures.PedidoFabricaSP.marcarPedidoSP'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION marcarpedidosp(integer,Date) OWNER TO postgres;


--PedidoPersonalSP

CREATE OR REPLACE FUNCTION crearpedido(Date,Date,integer)
  RETURNS void AS
'storedProcedures.PedidoPersonalSP.crearPedido'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION crearpedido(Date,Date,integer) OWNER TO postgres;

--ProductoSP

CREATE OR REPLACE FUNCTION modificacionproducto(integer,float8,integer)
  RETURNS void AS
'storedProcedures.ProductoSP.modificacionProducto'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION modificacionproducto(integer,float8,integer) OWNER TO postgres;



CREATE OR REPLACE FUNCTION asignaratipoprod(integer,integer)
  RETURNS void AS
'storedProcedures.ProductoSP.asignarATipoProd'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION asignaratipoprod(integer,integer) OWNER TO postgres;

CREATE OR REPLACE FUNCTION altadeproducto(integer,character varying ,float8, integer)
  RETURNS void AS
'storedProcedures.ProductoSP.altaDeProducto'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION altadeproducto(integer,character varying ,float8) OWNER TO postgres;


CREATE OR REPLACE FUNCTION bajadeproducto(integer)
  RETURNS boolean AS
'storedProcedures.ProductoSP.bajaDeProducto'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION bajadeproducto(integer) OWNER TO postgres;


--TipoDeProductoSP

CREATE OR REPLACE FUNCTION modificardescripciontipoproducto(integer, character varying)
  RETURNS void AS
'storedProcedures.TipoDeProductoSP.modificarDescripcionTipoProducto'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION modificardescripciontipoproducto(integer, character varying) OWNER TO postgres;


--TomoSP


CREATE OR REPLACE FUNCTION asignarprodtomo(integer, integer,character varying)
  RETURNS void AS
'storedProcedures.TomoSP.AsignarProdTomo'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION asignarprodtomo(integer, integer,character varying) OWNER TO postgres;

CREATE OR REPLACE FUNCTION desasignarprodtomo(integer, integer)
  RETURNS void AS
'storedProcedures.TomoSP.desasignarProdTomo'
  LANGUAGE javau IMMUTABLE;
ALTER FUNCTION desasignarprodtomo(integer, integer) OWNER TO postgres;







