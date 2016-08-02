using kamifer.LogManager;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace testing
{
    class Program
    {
        class objeto
        {
            public int MyProperty { get; set; }
        }
        static miLog log = null;
        public const string GET_TABLE_STRUCTURE = @"
                                        Select '[' + I.TABLE_SCHEMA + '].[' + I.TABLE_NAME + ']' AS 'NOMBRE_TABLA', 
		                                        I.COLUMN_NAME, I.DATA_TYPE, 
                                                convert(varchar, I.CHARACTER_MAXIMUM_LENGTH) as 'MAXLENGTH', 
		                                        convert(varchar, I.NUMERIC_PRECISION) AS 'PRECISION', 
		                                        convert(varchar, I.NUMERIC_SCALE) AS 'SCALE' 
		                                        From INFORMATION_SCHEMA.COLUMNS AS I 
		                                        where I.TABLE_NAME IN ({0});";

        static void Main(string[] args)
        {
           
            
            log = new miLog();
            log.LoadConfigXML("LogManager.xml");
            log.info("prueba");
            conectDB();
            try
            {
                objeto nuevo = null;
                nuevo.MyProperty = 32;

            }
            catch (Exception ex)
            {
                log.fatal("error fatal: " + ex.StackTrace , ex);
            }
            
            Console.WriteLine("pause...");
            Console.ReadLine();
        }

        static void conectDB()
        {


            SqlConnection conRemoto = new SqlConnection(@"Data Source=.\SQLEXPRESS;Initial Catalog=Chinook;");

            String nombresTablas ="";
            string sql = string.Format(GET_TABLE_STRUCTURE, string.Join(", ", nombresTablas));
            try
            {
                conRemoto.Open();
                SqlCommand cmdRemota = new SqlCommand(sql, conRemoto);
                SqlDataReader readerRemota = cmdRemota.ExecuteReader();
                DataTable tablaRemota = new DataTable();
                tablaRemota.Load(readerRemota);
            }
            catch (Exception ex)
            {
                log.fatal("Error db", ex);
            }
            finally
            {

            }
        }
    }
}
