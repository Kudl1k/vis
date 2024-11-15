module DataLibrary {
    requires static lombok;

    exports DataAccess.DataAccessObjects.Mappers;
    exports DataAccess.DataAccessObjects.Interface;
    exports DataAccess.DataAccessObjects.Sql;
//    exports DataAccess.DataAccessObjects.Text;
    exports DataAccess.Connectors;
    exports DataTransferObjects;

    requires java.sql;
}