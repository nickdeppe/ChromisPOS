//    Chromis POS  - The New Face of Open Source POS
//    Copyright (c) (c) 2015-2016
//    http://www.chromis.co.uk liquibase
//
//    This file is part of Chromis POS
//
//     Chromis POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Chromis POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Chromis POS.  If not, see <http://www.gnu.org/licenses/>.
//
package uk.chromis.pos.forms;

import uk.chromis.pos.promotion.PromotionInfo;
import uk.chromis.basic.BasicException;
import uk.chromis.data.loader.*;
import uk.chromis.data.model.Field;
import uk.chromis.data.model.Row;
import uk.chromis.format.Formats;
import uk.chromis.pos.customers.CustomerInfoExt;
import uk.chromis.pos.customers.CustomerTransaction;
import uk.chromis.pos.inventory.*;
import uk.chromis.pos.mant.FloorsInfo;
import uk.chromis.pos.payment.PaymentInfo;
import uk.chromis.pos.payment.PaymentInfoTicket;
import uk.chromis.pos.ticket.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

/**
 *
 * @author adrianromero
 */
public class DataLogicSales extends BeanFactoryDataSingle {

    protected Session s;
    protected Datas[] auxiliarDatas;
    protected Datas[] stockdiaryDatas;
    protected Datas[] stockAdjustDatas;
    protected SentenceExec m_sellvoucher;
    protected SentenceExec m_insertcat;
    protected Datas[] paymenttabledatas;
    private SentenceFind m_productname;
    protected Datas[] stockdatas;
    protected Row productsRow;
    protected Row featuresRow;
    protected Row showsRow;
    protected Row ratingsRow;
    protected Row showFeaturesQBFRow;
    protected Row showFeaturesRow;
    private String pName;
    private Double getTotal;
    private Double getTendered;
    private String getRetMsg;
    public static final String DEBT = "debt";
    public static final String DEBT_PAID = "debtpaid";
    protected static final String PREPAY = "prepay";
    private static final Logger logger = Logger.getLogger("uk.chromis.pos.forms.DataLogicSales");
    private String getCardName;
    private DataLogicSystem m_dlSystem;

    private SentenceExec m_updateRefund;

    // Use this INDEX_xxx instead of numbers to access arrays of product information
    public static int FIELD_COUNT = 0;
    public static int INDEX_ID = FIELD_COUNT++;
    public static int INDEX_REFERENCE = FIELD_COUNT++;
    public static int INDEX_CODE = FIELD_COUNT++;
    public static int INDEX_CODETYPE = FIELD_COUNT++;
    public static int INDEX_NAME = FIELD_COUNT++;
    public static int INDEX_ISCOM = FIELD_COUNT++;
    public static int INDEX_ISSCALE = FIELD_COUNT++;
    public static int INDEX_PRICEBUY = FIELD_COUNT++;
    public static int INDEX_PRICESELL = FIELD_COUNT++;
    public static int INDEX_CATEGORY = FIELD_COUNT++;
    public static int INDEX_TAXCAT = FIELD_COUNT++;
    public static int INDEX_ATTRIBUTESET_ID = FIELD_COUNT++;
    public static int INDEX_IMAGE = FIELD_COUNT++;
    public static int INDEX_ATTRIBUTES = FIELD_COUNT++;
    public static int INDEX_STOCKCOST = FIELD_COUNT++;
    public static int INDEX_STOCKVOLUME = FIELD_COUNT++;
    public static int INDEX_ISCATALOG = FIELD_COUNT++;
    public static int INDEX_CATORDER = FIELD_COUNT++;
    public static int INDEX_ISKITCHEN = FIELD_COUNT++;
    public static int INDEX_ISSERVICE = FIELD_COUNT++;
    public static int INDEX_DISPLAY = FIELD_COUNT++;
    public static int INDEX_ISVPRICE = FIELD_COUNT++;
    public static int INDEX_ISVERPATRIB = FIELD_COUNT++;
    public static int INDEX_TEXTTIP = FIELD_COUNT++;
    public static int INDEX_WARRANTY = FIELD_COUNT++;
    public static int INDEX_STOCKUNITS = FIELD_COUNT++;
    public static int INDEX_ALIAS = FIELD_COUNT++;
    public static int INDEX_ALWAYSAVAILABLE = FIELD_COUNT++;
    public static int INDEX_DISCOUNTED = FIELD_COUNT++;
    public static int INDEX_CANDISCOUNT = FIELD_COUNT++;
    public static int INDEX_ISPACK = FIELD_COUNT++;
    public static int INDEX_PACKQUANTITY = FIELD_COUNT++;
    public static int INDEX_PACKPRODUCT = FIELD_COUNT++;
    public static int INDEX_PROMOTIONID = FIELD_COUNT++;
    public static int INDEX_MANAGESTOCK = FIELD_COUNT++;
    public static int INDEX_ISBOXOFFICE = FIELD_COUNT++;

    
    public static int FEATURES_FIELD_COUNT = 0;
    public static int INDEX_FEATURE_ID = FEATURES_FIELD_COUNT++;
    public static int INDEX_FEATURE_NAME = FEATURES_FIELD_COUNT++;
    public static int INDEX_FEATURE_IMAGE = FEATURES_FIELD_COUNT++;
    public static int INDEX_FEATURE_RUNTIME = FEATURES_FIELD_COUNT++;
    public static int INDEX_FEATURE_RATINGID = FEATURES_FIELD_COUNT++;
    public static int INDEX_FEATURE_ACTIVE = FEATURES_FIELD_COUNT++;

    public static int SHOWS_FIELD_COUNT = 0;
    public static int INDEX_SHOW_ID = SHOWS_FIELD_COUNT++;
    public static int INDEX_SHOW_THEATREID = SHOWS_FIELD_COUNT++;
    public static int INDEX_SHOW_STARTDATE = SHOWS_FIELD_COUNT++;
    public static int INDEX_SHOW_ENDDATE = SHOWS_FIELD_COUNT++;
    public static int INDEX_SHOW_REPORTSTARTDATE = SHOWS_FIELD_COUNT++;
    public static int INDEX_SHOW_REPORTENDDATE = SHOWS_FIELD_COUNT++;
    
    public static int RATINGS_FIELD_COUNT = 0;
    public static int INDEX_RATING_ID = RATINGS_FIELD_COUNT++;
    public static int INDEX_RATING_NAME = RATINGS_FIELD_COUNT++;
    public static int INDEX_RATING_ACTIVE = RATINGS_FIELD_COUNT++;
    
    
    public static int SHOW_FEATURES_FIELD_COUNT = 0;
    public static int INDEX_SHOW_FEATURES_ID = SHOW_FEATURES_FIELD_COUNT++;
    public static int INDEX_SHOW_FEATURES_SHOWID = SHOW_FEATURES_FIELD_COUNT++;
    public static int INDEX_SHOW_FEATURES_FEATUREID = SHOW_FEATURES_FIELD_COUNT++;
    public static int INDEX_SHOW_FEATURES_SEQUENCE = SHOW_FEATURES_FIELD_COUNT++;
    public static int INDEX_SHOW_FEATURES_STARTTIME = SHOW_FEATURES_FIELD_COUNT++;

    
    /**
     * Creates a new instance of SentenceContainerGeneric
     */
    public DataLogicSales() {

        stockAdjustDatas = new Datas[]{
            Datas.STRING,
            Datas.STRING,
            Datas.STRING,
            Datas.DOUBLE
        };
        stockdiaryDatas = new Datas[]{
            Datas.STRING, // 0 - ID  
            Datas.TIMESTAMP, // 1- Time  
            Datas.INT, // 2 - Reason  
            Datas.STRING, // 3 - Location  
            Datas.STRING, // 4 - Product  
            Datas.STRING, // 5 - Attribute  
            Datas.DOUBLE, // 6 - Units  
            Datas.DOUBLE, // 7 - Price  
            Datas.STRING, // 8 - User  
            Datas.STRING, // 9 - Product Reference  
            Datas.STRING, // 10 - Product Code  
            Datas.STRING, // 11 - Product Name  
            Datas.STRING, // 12 - Attribute set ID  
            Datas.STRING, // 13 - Attribute set inst desc  
            Datas.DOUBLE, // 14 - Units in stock  
            Datas.DOUBLE, // 15 - Stock Security  
            Datas.DOUBLE, // 16 - Stock Maximum  
            Datas.DOUBLE, // 17 - Buy Price  
            Datas.DOUBLE // 18 - Sell Price 
        };
        paymenttabledatas = new Datas[]{
            Datas.STRING,
            Datas.STRING,
            Datas.TIMESTAMP,
            Datas.STRING,
            Datas.STRING,
            Datas.DOUBLE,
            Datas.STRING};
        stockdatas = new Datas[]{
            Datas.STRING,
            Datas.STRING,
            Datas.STRING,
            Datas.DOUBLE,
            Datas.DOUBLE,
            Datas.DOUBLE};
        auxiliarDatas = new Datas[]{
            Datas.STRING,
            Datas.STRING,
            Datas.STRING,
            Datas.STRING,
            Datas.STRING,
            Datas.STRING};

        productsRow = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field(AppLocal.getIntString("label.prodref"), Datas.STRING, Formats.STRING, true, true, true),
                new Field(AppLocal.getIntString("label.prodbarcode"), Datas.STRING, Formats.STRING, false, true, true),
                new Field(AppLocal.getIntString("label.prodbarcodetype"), Datas.STRING, Formats.STRING, false, true, true),
                new Field(AppLocal.getIntString("label.prodname"), Datas.STRING, Formats.STRING, true, true, true),
                new Field("ISCOM", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ISSCALE", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field(AppLocal.getIntString("label.prodpricebuy"), Datas.DOUBLE, Formats.CURRENCY, false, true, true),
                new Field(AppLocal.getIntString("label.prodpricesell"), Datas.DOUBLE, Formats.CURRENCY, false, true, true),
                new Field(AppLocal.getIntString("label.prodcategory"), Datas.STRING, Formats.STRING, false, false, true),
                new Field(AppLocal.getIntString("label.taxcategory"), Datas.STRING, Formats.STRING, false, false, true),
                new Field(AppLocal.getIntString("label.attributeset"), Datas.STRING, Formats.STRING, false, false, true),
                new Field("IMAGE", Datas.IMAGE, Formats.NULL),
                new Field("ATTRIBUTES", Datas.BYTES, Formats.NULL),
                new Field("STOCKCOST", Datas.DOUBLE, Formats.CURRENCY),
                new Field("STOCKVOLUME", Datas.DOUBLE, Formats.DOUBLE),
                new Field("ISCATALOG", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("CATORDER", Datas.INT, Formats.INT),
                new Field("ISKITCHEN", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ISSERVICE", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field(AppLocal.getIntString("label.display"), Datas.STRING, Formats.STRING, false, true, true),
                new Field("ISVPRICE", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ISVERPATRIB", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("TEXTTIP", Datas.STRING, Formats.STRING),
                new Field("WARRANTY", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field(AppLocal.getIntString("label.stockunits"), Datas.DOUBLE, Formats.DOUBLE),
                new Field("ALIAS", Datas.STRING, Formats.STRING), //26
                new Field("ALWAYSAVAILABLE", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("DISCOUNTED", Datas.STRING, Formats.STRING),
                new Field("CANDISCOUNT", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ISPACK", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("PACKQUANTITY", Datas.DOUBLE, Formats.DOUBLE),
                new Field("PACKPRODUCT", Datas.STRING, Formats.STRING),
                new Field("PROMOTIONID", Datas.STRING, Formats.STRING),
                new Field("MANAGESTOCK", Datas.BOOLEAN, Formats.BOOLEAN),
                new Field("ISBOXOFFICE", Datas.BOOLEAN, Formats.BOOLEAN)
        );

        // If this fails there is a coding error - have you added a column
        // to the PRODUCTS table and not added an INDEX_xxx for it?
        assert (FIELD_COUNT == productsRow.getFields().length);
        
        
        
        
        featuresRow = new Row(
            new Field("ID", Datas.STRING, Formats.STRING),
            new Field("NAME", Datas.STRING, Formats.STRING, true, true, true),
            new Field("IMAGE", Datas.IMAGE, Formats.NULL),
            new Field("RUNTIME", Datas.INT, Formats.INT),
            new Field("RATINGID", Datas.STRING, Formats.STRING),
            new Field("ACTIVE", Datas.BOOLEAN, Formats.BOOLEAN, true, true, true)
        );
        
        assert (FEATURES_FIELD_COUNT == featuresRow.getFields().length);

        
        

        showsRow = new Row(
            new Field("ID", Datas.STRING, Formats.STRING),
            new Field("THEATREID", Datas.STRING, Formats.STRING, true, true, true),
            new Field("STARTDATE", Datas.DATE, Formats.DATE),
            new Field("ENDDATE", Datas.DATE, Formats.DATE),
            new Field("REPORTSTARTDATE", Datas.DATE, Formats.DATE),
            new Field("REPORTSTARTDATE", Datas.DATE, Formats.DATE)
        );
        
        assert (SHOWS_FIELD_COUNT == showsRow.getFields().length );
        
        
        ratingsRow = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field("NAME", Datas.STRING, Formats.STRING),
                new Field("ACTIVE", Datas.BOOLEAN, Formats.BOOLEAN)
        );
        assert (RATINGS_FIELD_COUNT == ratingsRow.getFields().length );
        
        
        showFeaturesQBFRow = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field("SHOWID", Datas.STRING, Formats.STRING),
                new Field("FEATUREID", Datas.STRING, Formats.STRING),
                new Field("NAME", Datas.STRING, Formats.STRING),
                new Field("SEQUENCE", Datas.INT, Formats.INT),
                new Field("STARTTIME", Datas.STRING, Formats.STRING)
        );
        
        showFeaturesRow = new Row(
                new Field("ID", Datas.STRING, Formats.STRING),
                new Field("SHOWID", Datas.STRING, Formats.STRING),
                new Field("FEATUREID", Datas.STRING, Formats.STRING),
                new Field("SEQUENCE", Datas.INT, Formats.INT),
                new Field("STARTTIME", Datas.STRING, Formats.STRING)
        );
        
        assert (SHOW_FEATURES_FIELD_COUNT == showFeaturesRow.getFields().length );
        
    }

    private String getSelectFieldList() {
        String sel = "P.ID, "
                + "P.REFERENCE, "
                + "P.CODE, "
                + "P.CODETYPE, "
                + "P.NAME, "
                + "P.ISCOM, "
                + "P.ISSCALE, "
                + "P.PRICEBUY, "
                + "P.PRICESELL, "
                + "P.CATEGORY, "
                + "P.TAXCAT, "
                + "P.ATTRIBUTESET_ID, "
                + "P.IMAGE, "
                + "P.ATTRIBUTES, "
                + "P.STOCKCOST, "
                + "P.STOCKVOLUME, "
                + "P.ISCATALOG, "
                + "P.CATORDER, "
                + "P.ISKITCHEN, "
                + "P.ISSERVICE, "
                + "P.DISPLAY, "
                + "P.ISVPRICE, "
                + "P.ISVERPATRIB, "
                + "P.TEXTTIP, "
                + "P.WARRANTY, "
                + "P.STOCKUNITS, "
                + "P.ALIAS, "
                + "P.ALWAYSAVAILABLE, "
                + "P.DISCOUNTED, "
                + "P.CANDISCOUNT, "
                + "P.ISPACK, P.PACKQUANTITY, P.PACKPRODUCT, "
                + "P.PROMOTIONID, "
                + "P.MANAGESTOCK, "
                + "P.ISBOXOFFICE ";
        return sel;
    }

    /**
     *
     * @param s
     */
    @Override
    public void init(Session s) {
        this.s = s;

        m_updateRefund = new StaticSentence(s, "UPDATE TICKETLINES SET REFUNDQTY = ? WHERE TICKET = ? AND LINE = ?  ", new SerializerWriteBasic(new Datas[]{
            Datas.DOUBLE,
            Datas.STRING,
            Datas.INT
        }));

        m_sellvoucher = new StaticSentence(s, "INSERT INTO VOUCHERS ( VOUCHER, SOLDTICKETID) "
                + "VALUES (?, ?)", new SerializerWriteBasic(new Datas[]{
            Datas.STRING,
            Datas.STRING}));

        m_productname = new StaticSentence(s, "SELECT NAME FROM PRODUCTS WHERE ID = ? ",
                SerializerWriteString.INSTANCE, SerializerReadString.INSTANCE);

        m_insertcat = new StaticSentence(s, "INSERT INTO CATEGORIES ( ID, NAME, CATSHOWNAME ) "
                + "VALUES (?, ?, ?)", new SerializerWriteBasic(new Datas[]{
            Datas.STRING,
            Datas.STRING,
            Datas.BOOLEAN}));
    }

    /**
     *
     * @param id
     * @return
     * @throws BasicException
     */
    public final Object getCategoryColour(String id) throws BasicException {

        return new PreparedSentence(s,
                "SELECT COLOUR FROM CATEGORIES WHERE ID = ?",
                SerializerWriteString.INSTANCE,
                SerializerReadString.INSTANCE).find(id);
    }

    /**
     *
     * @return
     */
    public final Row getProductsRow() {
        return productsRow;
    }

    public final String getCurrentLocationStock(String id) throws BasicException {
        m_dlSystem = new DataLogicSystem();
        m_dlSystem.init(s);
        Properties m_propsdb = m_dlSystem.getResourceAsProperties(AppConfig.getInstance().getHost() + "/properties");

        Object[] record = (Object[]) new StaticSentence(s, "SELECT UNITS FROM STOCKCURRENT WHERE  LOCATION = " + m_propsdb.getProperty("location") + " AND PRODUCT = ? ", SerializerWriteString.INSTANCE, new SerializerReadBasic(new Datas[]{Datas.STRING})).find(id);
        return record == null ? "0.0" : (String) record[0];
    }

    /**
     *
     * @param id
     * @return
     * @throws BasicException
     */
    public final ProductInfoExt getProductInfo(String id) throws BasicException {
        return (ProductInfoExt) new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM STOCKCURRENT C LEFT JOIN PRODUCTS P ON (C.PRODUCT = P.ID) "
                + "WHERE ID = ?", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find(id);
    }

    /**
     *
     * @param sCode
     * @return
     * @throws BasicException
     */
    public final ProductInfoExt getProductInfoByCode(String sCode) throws BasicException {
        if (sCode.startsWith("977")) {
            // This is an ISSN barcode (news and magazines) 
            // the first 3 digits correspond to the 977 prefix assigned to serial publications, 
            // the next 7 digits correspond to the ISSN of the publication 
            // Anything after that is publisher dependant - we strip everything after  
            // the 10th character 
            sCode = sCode.substring(0, 10);
        }
        return (ProductInfoExt) new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM STOCKCURRENT C RIGHT JOIN PRODUCTS P ON (C.PRODUCT = P.ID) "
                + " WHERE CODE = ? ", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find(sCode);
    }

    /**
     *
     * @param sReference
     * @return
     * @throws BasicException
     */
    public final ProductInfoExt getProductInfoByReference(String sReference) throws BasicException {
        return (ProductInfoExt) new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM STOCKCURRENT C RIGHT JOIN PRODUCTS P ON (C.PRODUCT = P.ID) "
                + "WHERE REFERENCE = ?", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find(sReference);
    }

    public final ProductInfoExt getProductInfoNoSC(String id) throws BasicException {
        return (ProductInfoExt) new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM PRODUCTS P WHERE P.ID = ? "
                + "ORDER BY P.ID, P.REFERENCE, P.NAME ", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).find(id);
    }

    /**
     *
     * @return @throws BasicException
     */
    public final List<CategoryInfo> getRootCategories() throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "IMAGE, "
                + "TEXTTIP, "
                + "CATSHOWNAME, "
                + "COLOUR, "
                + "CATORDER "
                + "FROM CATEGORIES "
                + "WHERE PARENTID IS NULL AND CATSHOWNAME = " + s.DB.TRUE() + " "
                + "ORDER BY NAME", null, CategoryInfo.getSerializerRead()).list();
    }

    /**
     *
     * @return @throws BasicException
     */
    public final List<CategoryInfo> getRootCategoriesByCatOrder() throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "IMAGE, "
                + "TEXTTIP, "
                + "CATSHOWNAME, "
                + "COLOUR, "
                + "CATORDER "
                + "FROM CATEGORIES "
                + "WHERE PARENTID IS NULL AND CATSHOWNAME = " + s.DB.TRUE() + " AND CATORDER IS NOT NULL "
                + "ORDER BY CATORDER", null, CategoryInfo.getSerializerRead()).list();
    }

    public final List<CategoryInfo> getRootCategoriesByName() throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "IMAGE, "
                + "TEXTTIP, "
                + "CATSHOWNAME, "
                + "COLOUR, "
                + "CATORDER "
                + "FROM CATEGORIES "
                + "WHERE PARENTID IS NULL AND CATSHOWNAME = " + s.DB.TRUE() + " AND CATORDER IS NULL "
                + "ORDER BY NAME", null, CategoryInfo.getSerializerRead()).list();
    }

    /**
     *
     * @param category
     * @return
     * @throws BasicException
     */
    public final List<CategoryInfo> getSubcategories(String category) throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "IMAGE, "
                + "TEXTTIP, "
                + "CATSHOWNAME, "
                + "COLOUR, "
                + "CATORDER "
                + "FROM CATEGORIES WHERE PARENTID = ? ORDER BY NAME", SerializerWriteString.INSTANCE, CategoryInfo.getSerializerRead()).list(category);
    }

    public final List<CategoryInfo> getSubcategoriesByCatOrder(String category) throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "IMAGE, "
                + "TEXTTIP, "
                + "CATSHOWNAME, "
                + "COLOUR, "
                + "CATORDER "
                + "FROM CATEGORIES WHERE PARENTID = ? AND CATORDER IS NOT NULL ORDER BY CATORDER", SerializerWriteString.INSTANCE, CategoryInfo.getSerializerRead()).list(category);
    }

    public final List<CategoryInfo> getSubcategoriesByName(String category) throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "IMAGE, "
                + "TEXTTIP, "
                + "CATSHOWNAME, "
                + "COLOUR, "
                + "CATORDER "
                + "FROM CATEGORIES WHERE PARENTID = ? AND CATORDER IS NULL ORDER BY NAME", SerializerWriteString.INSTANCE, CategoryInfo.getSerializerRead()).list(category);
    }

    /**
     *
     * @param category
     * @return
     * @throws BasicException
     */
    public List<ProductInfoExt> getProductCatalog(String category) throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM PRODUCTS P "
                + "WHERE (P.ISCATALOG = " + s.DB.TRUE() + " AND P.CATEGORY = ?) OR (P.ALWAYSAVAILABLE = " + s.DB.TRUE() + ") "
                + "ORDER BY P.CATORDER, P.NAME ", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).list(category);
    }

    /**
     *
     * @param category
     * @return
     * @throws BasicException
     */
    public List<ProductInfoExt> getAllProductCatalogByCatOrder() throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM PRODUCTS P "
                + "WHERE P.ISCATALOG = " + s.DB.TRUE() + " "
                + "ORDER BY P.CATORDER, P.NAME ", null, ProductInfoExt.getSerializerRead()).list();
    }

    public List<ProductInfoExt> getAllNonProductCatalog() throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM PRODUCTS P "
                + "WHERE P.ISCATALOG = " + s.DB.FALSE() + " "
                + "ORDER BY P.CATEGORY, P.NAME ", null, ProductInfoExt.getSerializerRead()).list();
    }

    /**
     *
     * @param category
     * @return
     * @throws BasicException
     */
    public List<ProductInfoExt> getAllProductCatalog() throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM PRODUCTS P "
                + "WHERE P.ISCATALOG = " + s.DB.TRUE() + " "
                + "ORDER BY P.CATEGORY, P.NAME ", null, ProductInfoExt.getSerializerRead()).list();
    }

    /**
     *
     * @param category
     * @return
     * @throws BasicException
     */
    public List<ProductInfoExt> getProductCatalogAlways() throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM CATEGORIES C INNER JOIN PRODUCTS P ON (P.CATEGORY = C.ID) "
                + "WHERE P.ALWAYSAVAILABLE = " + s.DB.TRUE() + " "
                + "ORDER BY  C.NAME, P.NAME",
                null,
                ProductInfoExt.getSerializerRead()).list();

    }

    public List<ProductInfoExt> getProductNonCatalog(String category) throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM PRODUCTS P "
                + "WHERE P.ISCATALOG = " + s.DB.FALSE() + " "
                + "AND P.CATEGORY = ? "
                + "ORDER BY P.NAME ", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).list(category);
    }

    /**
     *
     * @param id
     * @return
     * @throws BasicException
     */
    public List<ProductInfoExt> getProductComments(String id) throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + getSelectFieldList()
                + "FROM PRODUCTS P, PRODUCTS_COM M "
                + "WHERE P.ISCATALOG = " + s.DB.TRUE() + " "
                + "AND P.ID = M.PRODUCT2 AND M.PRODUCT = ? "
                + "AND P.ISCOM = " + s.DB.TRUE() + " "
                + "ORDER BY P.CATORDER, P.NAME", SerializerWriteString.INSTANCE, ProductInfoExt.getSerializerRead()).list(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws BasicException
     */
    public final CategoryInfo getCategoryInfo(String id) throws BasicException {
        return (CategoryInfo) new PreparedSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "IMAGE, "
                + "TEXTTIP, "
                + "CATSHOWNAME, "
                + "COLOUR, "
                + "CATORDER "
                + "FROM CATEGORIES "
                + "WHERE ID = ? "
                + "ORDER BY NAME", SerializerWriteString.INSTANCE, CategoryInfo.getSerializerRead()).find(id);
    }

    public final SentenceList getProductList() {
        m_dlSystem = new DataLogicSystem();
        m_dlSystem.init(s);
        Properties m_propsdb = m_dlSystem.getResourceAsProperties(AppConfig.getInstance().getHost() + "/properties");
        return new StaticSentence(s, new QBFBuilder(
                "SELECT "
                + "P.ID, "
                + "P.REFERENCE, "
                + "P.CODE, "
                + "P.CODETYPE, "
                + "P.NAME, "
                + "P.ISCOM, "
                + "P.ISSCALE, "
                + "P.PRICEBUY, "
                + "P.PRICESELL, "
                + "P.CATEGORY, "
                + "P.TAXCAT, "
                + "P.ATTRIBUTESET_ID, "
                + "P.IMAGE, "
                + "P.ATTRIBUTES, "
                + "P.STOCKCOST, "
                + "P.STOCKVOLUME, "
                + "P.ISCATALOG, "
                + "P.CATORDER, "
                + "P.ISKITCHEN, "
                + "P.ISSERVICE, "
                + "P.DISPLAY, "
                + "P.ISVPRICE, "
                + "P.ISVERPATRIB, "
                + "P.TEXTTIP, "
                + "P.WARRANTY, "
                + "( SELECT UNITS FROM STOCKCURRENT WHERE  LOCATION = " + m_propsdb.getProperty("location") + " AND PRODUCT = P.ID ), "
                + "P.ALIAS, "
                + "P.ALWAYSAVAILABLE, "
                + "P.DISCOUNTED, "
                + "P.CANDISCOUNT, "
                + "P.ISPACK, P.PACKQUANTITY, P.PACKPRODUCT, "
                + "P.PROMOTIONID, "
                + "P.MANAGESTOCK "
                + "FROM STOCKCURRENT C RIGHT OUTER JOIN PRODUCTS P ON (C.PRODUCT = P.ID) "
                + "WHERE ?(QBF_FILTER) "
                + "ORDER BY P.REFERENCE, P.NAME",
                new String[]{"P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.CATEGORY", "P.CODE", "C.UNITS"}), new SerializerWriteBasic(new Datas[]{
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.DOUBLE,}), ProductInfoExt.getSerializerRead());
    }

    public SentenceList getProductListNormal() {
        return new StaticSentence(s, new QBFBuilder(
                "SELECT "
                + getSelectFieldList()
                + "FROM STOCKCURRENT C RIGHT OUTER JOIN PRODUCTS P ON (C.PRODUCT = P.ID) "
                + "WHERE P.ISCOM = " + s.DB.FALSE() + " AND ?(QBF_FILTER) "
                + "ORDER BY P.REFERENCE, P.NAME",
                new String[]{"P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.CATEGORY", "P.CODE", "C.UNITS"}),
                new SerializerWriteBasic(new Datas[]{
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.DOUBLE
        }), ProductInfoExt.getSerializerRead());
    }

    public SentenceList getProductListAuxiliar() {
        return new StaticSentence(s, new QBFBuilder(
                "SELECT "
                + getSelectFieldList()
                + "FROM STOCKCURRENT C RIGHT OUTER JOIN PRODUCTS P ON (C.PRODUCT = P.ID) "
                + "WHERE P.ISCOM = " + s.DB.TRUE() + " AND ?(QBF_FILTER) "
                + "ORDER BY P.REFERENCE",
                new String[]{"P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.CATEGORY", "P.CODE"}),
                new SerializerWriteBasic(new Datas[]{
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.STRING}),
                ProductInfoExt.getSerializerRead());
    }

    //Tickets and Receipt list
    /**
     *
     * @return
     */
    public SentenceList getTicketsList() {
        return new StaticSentence(s, new QBFBuilder(
                "SELECT "
                + "T.TICKETID, "
                + "T.TICKETTYPE, "
                + "R.DATENEW, "
                + "P.NAME, "
                + "C.NAME, "
                + "SUM(PM.TOTAL) "
                + "FROM RECEIPTS "
                + "R JOIN TICKETS T ON R.ID = T.ID LEFT OUTER JOIN PAYMENTS PM "
                + "ON R.ID = PM.RECEIPT LEFT OUTER JOIN CUSTOMERS C "
                + "ON C.ID = T.CUSTOMER LEFT OUTER JOIN PEOPLE P ON T.PERSON = P.ID "
                + "WHERE ?(QBF_FILTER) "
                + "GROUP BY "
                + "T.ID, "
                + "T.TICKETID, "
                + "T.TICKETTYPE, "
                + "R.DATENEW, "
                + "P.NAME, "
                + "C.NAME "
                + "ORDER BY R.DATENEW DESC, T.TICKETID",
                new String[]{"T.TICKETID", "T.TICKETTYPE", "PM.TOTAL", "R.DATENEW", "R.DATENEW", "P.NAME", "C.NAME"}), new SerializerWriteBasic(new Datas[]{
            Datas.OBJECT, Datas.INT,
            Datas.OBJECT, Datas.INT,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.TIMESTAMP,
            Datas.OBJECT, Datas.TIMESTAMP,
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.STRING}), new SerializerReadClass(FindTicketsInfo.class));
    }

    //User list
    /**
     *
     * @return
     */
    public final SentenceList getUserList() {
        return new StaticSentence(s, "SELECT "
                + "ID, "
                + "NAME "
                + "FROM PEOPLE "
                + "ORDER BY NAME", null, new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new TaxCategoryInfo(
                        dr.getString(1),
                        dr.getString(2));
            }
        });
    }

    // Listados para combo
    /**
     *
     * @return
     */
    public final SentenceList getTaxList() {
        return new StaticSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "CATEGORY, "
                + "CUSTCATEGORY, "
                + "PARENTID, "
                + "RATE, "
                + "RATECASCADE, "
                + "RATEORDER "
                + "FROM TAXES "
                + "ORDER BY NAME", null, new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new TaxInfo(
                        dr.getString(1),
                        dr.getString(2),
                        dr.getString(3),
                        dr.getString(4),
                        dr.getString(5),
                        dr.getDouble(6),
                        dr.getBoolean(7),
                        dr.getInt(8));
            }
        });
    }

    /**
     *
     * @return
     */
    public final SentenceList getCategoriesList() {
        if (DataLogicSystem.m_dbVersion.equals("Derby")) {
            return new StaticSentence(s, "SELECT "
                    + "ID, "
                    + "NAME, "
                    + "IMAGE, "
                    + "TEXTTIP, "
                    + "CATSHOWNAME, "
                    + "COLOUR, "
                    + "CATORDER "
                    + "FROM CATEGORIES "
                    + "ORDER BY NAME", null, CategoryInfo.getSerializerRead());
        } else {
            return new StaticSentence(s, "SELECT "
                    + "ID, "
                    + "NAME, "
                    + "NULL, "
                    + "TEXTTIP, "
                    + "CATSHOWNAME, "
                    + "COLOUR, "
                    + "CATORDER "
                    + "FROM CATEGORIES "
                    + "ORDER BY NAME", null, CategoryInfo.getSerializerRead());
        }
    }

    /**
     *
     * @return
     */
    public final SentenceList getPromotionsList() {
        return new StaticSentence(s, "SELECT "
                + "ID, NAME, CRITERIA, SCRIPT, ISENABLED, ALLPRODUCTS "
                + "FROM PROMOTIONS "
                + "ORDER BY NAME", null,
                new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new PromotionInfo(dr.getString(1), dr.getString(2),
                        Formats.BYTEA.formatValue(dr.getBytes(3)),
                        Formats.BYTEA.formatValue(dr.getBytes(4)),
                        dr.getBoolean(5),
                        dr.getBoolean(6));
            }
        });
    }

    /**
     *
     * @return
     */
    public final SentenceList getTaxCustCategoriesList() {
        return new StaticSentence(s, "SELECT "
                + "ID, "
                + "NAME "
                + "FROM TAXCUSTCATEGORIES "
                + "ORDER BY NAME", null, new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new TaxCustCategoryInfo(dr.getString(1), dr.getString(2));
            }
        });
    }

    /**
     *
     * @return @throws BasicException
     */
    @SuppressWarnings("unchecked")
    public final List<CustomerTransaction> getCustomersTransactionList(String name) throws BasicException {
        return (List<CustomerTransaction>) new PreparedSentence(s,
                "SELECT TICKETS.TICKETID, PRODUCTS.NAME AS PNAME, "
                + "SUM(TICKETLINES.UNITS) AS UNITS, "
                + "SUM(TICKETLINES.UNITS * TICKETLINES.PRICE) AS AMOUNT, "
                + "SUM(TICKETLINES.UNITS * TICKETLINES.PRICE * (1.0 + TAXES.RATE)) AS TOTAL, "
                + "RECEIPTS.DATENEW, CUSTOMERS.NAME AS CNAME "
                + "FROM RECEIPTS, CUSTOMERS, TICKETS, TICKETLINES "
                + "LEFT OUTER JOIN PRODUCTS ON TICKETLINES.PRODUCT = PRODUCTS.ID "
                + "LEFT OUTER JOIN TAXES ON TICKETLINES.TAXID = TAXES.ID  "
                + "WHERE CUSTOMERS.ID = TICKETS.CUSTOMER AND TICKETLINES.PRODUCT = PRODUCTS.ID AND RECEIPTS.ID = TICKETS.ID AND TICKETS.ID = TICKETLINES.TICKET "
                + "AND CUSTOMERS.NAME = ?"
                + "GROUP BY CUSTOMERS.NAME, RECEIPTS.DATENEW, TICKETS.TICKETID, PRODUCTS.NAME, TICKETS.TICKETTYPE "
                + "ORDER BY RECEIPTS.DATENEW DESC, PRODUCTS.NAME",
                SerializerWriteString.INSTANCE,
                CustomerTransaction.getSerializerRead()).list(name);
    }

    /**
     *
     * @param productId The product id to look for kit
     * @return List of products part of the searched product
     * @throws BasicException
     */
    public final List<ProductsRecipeInfo> getProductsKit(String productId) throws BasicException {
        return new PreparedSentence(s, "SELECT "
                + "ID, "
                + "PRODUCT, "
                + "PRODUCT_KIT, "
                + "QUANTITY "
                + "FROM PRODUCTS_KIT WHERE PRODUCT = ? ", SerializerWriteString.INSTANCE, ProductsRecipeInfo.getSerializerRead()).list(productId);
    }

    /**
     *
     * @return
     */
    public final SentenceList getTaxCategoriesList() {
        return new StaticSentence(s, "SELECT "
                + "ID, "
                + "NAME "
                + "FROM TAXCATEGORIES "
                + "ORDER BY NAME", null, new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new TaxCategoryInfo(dr.getString(1), dr.getString(2));
            }
        });
    }

    /**
     *
     * @return
     */
    public final SentenceList getAttributeSetList() {
        return new StaticSentence(s, "SELECT "
                + "ID, "
                + "NAME "
                + "FROM ATTRIBUTESET "
                + "ORDER BY NAME", null, new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new AttributeSetInfo(dr.getString(1), dr.getString(2));
            }
        });
    }

    /**
     *
     * @return
     */
    public final SentenceList getLocationsList() {
        return new StaticSentence(s, "SELECT "
                + "ID, "
                + "NAME, "
                + "ADDRESS FROM LOCATIONS "
                + "ORDER BY NAME", null, new SerializerReadClass(LocationInfo.class));
    }

    public final SentenceList getProductListList() {
        return new StaticSentence(s, "SELECT DISTINCT "
                + "LISTNAME FROM PRODUCTLISTS "
                + "ORDER BY LISTNAME", null, new SerializerReadClass(ProductListInfo.class));
    }

    public final SentenceList getProductListItems(String listName) {
        return new StaticSentence(s, "SELECT "
                + "L.PRODUCT, P.REFERENCE, P.NAME FROM PRODUCTLISTS L LEFT JOIN PRODUCTS P "
                + "ON P.ID = L.PRODUCT "
                + "WHERE L.LISTNAME = '" + listName + "' "
                + "ORDER BY P.REFERENCE ",
                null, new SerializerReadClass(ProductListItem.class));
    }

    /**
     *
     * @return
     */
    public final SentenceList getFloorsList() {
        return new StaticSentence(s, "SELECT ID, NAME FROM FLOORS ORDER BY NAME", null, new SerializerReadClass(FloorsInfo.class));
    }

    /**
     *
     * @param card
     * @return
     * @throws BasicException
     */
    public CustomerInfoExt findCustomerExt(String card) throws BasicException {
        return (CustomerInfoExt) new PreparedSentence(s, "SELECT "
                + "ID, "
                + "TAXID, "
                + "SEARCHKEY, "
                + "NAME, "
                + "CARD, "
                + "TAXCATEGORY, "
                + "NOTES, "
                + "MAXDEBT, "
                + "VISIBLE, "
                + "CURDATE, "
                + "CURDEBT, "
                + "FIRSTNAME, "
                + "LASTNAME, "
                + "EMAIL, "
                + "PHONE, "
                + "PHONE2, "
                + "FAX, "
                + "ADDRESS, "
                + "ADDRESS2, "
                + "POSTAL, "
                + "CITY, "
                + "REGION, "
                + "COUNTRY, "
                + "IMAGE, "
                + "DOB, "
                + "DISCOUNT "
                + "FROM CUSTOMERS "
                + "WHERE CARD = ? AND VISIBLE = " + s.DB.TRUE() + " "
                + "ORDER BY NAME", SerializerWriteString.INSTANCE, new CustomerExtRead()).find(card);
    }

    /**
     *
     * @param id
     * @return
     * @throws BasicException
     */
    public CustomerInfoExt loadCustomerExt(String id) throws BasicException {
        return (CustomerInfoExt) new PreparedSentence(s, "SELECT "
                + "ID, "
                + "TAXID, "
                + "SEARCHKEY, "
                + "NAME, "
                + "CARD, "
                + "TAXCATEGORY, "
                + "NOTES, "
                + "MAXDEBT, "
                + "VISIBLE, "
                + "CURDATE, "
                + "CURDEBT, "
                + "FIRSTNAME, "
                + "LASTNAME, "
                + "EMAIL, "
                + "PHONE, "
                + "PHONE2, "
                + "FAX, "
                + "ADDRESS, "
                + "ADDRESS2, "
                + "POSTAL, "
                + "CITY, "
                + "REGION, "
                + "COUNTRY, "
                + "IMAGE, "
                + "DOB, "
                + "DISCOUNT "
                + "FROM CUSTOMERS WHERE ID = ?", SerializerWriteString.INSTANCE, new CustomerExtRead()).find(id);
    }

    /**
     *
     * @param id
     * @return
     * @throws BasicException
     */
    public final boolean isCashActive(String id) throws BasicException {

        return new PreparedSentence(s,
                "SELECT MONEY FROM CLOSEDCASH WHERE DATEEND IS NULL AND MONEY = ?",
                SerializerWriteString.INSTANCE,
                SerializerReadString.INSTANCE).find(id)
                != null;
    }

    /**
     *
     * @param tickettype
     * @param ticketid
     * @return
     * @throws BasicException
     */
    public final TicketInfo loadTicket(final int tickettype, final int ticketid) throws BasicException {
        TicketInfo ticket = (TicketInfo) new PreparedSentence(s, "SELECT "
                + "T.ID, "
                + "T.TICKETTYPE, "
                + "T.TICKETID, "
                + "R.DATENEW, "
                + "R.MONEY, "
                + "R.ATTRIBUTES, "
                + "P.ID, "
                + "P.NAME, "
                + "T.CUSTOMER "
                + "FROM RECEIPTS R "
                + "JOIN TICKETS T ON R.ID = T.ID "
                + "LEFT OUTER JOIN PEOPLE P ON T.PERSON = P.ID "
                + "WHERE T.TICKETTYPE = ? AND T.TICKETID = ? "
                + "ORDER BY R.DATENEW DESC", SerializerWriteParams.INSTANCE, new SerializerReadClass(TicketInfo.class))
                .find(new DataParams() {
                    @Override
                    public void writeValues() throws BasicException {
                        setInt(1, tickettype);
                        setInt(2, ticketid);
                    }
                });
        if (ticket != null) {

            String customerid = ticket.getCustomerId();
            ticket.setCustomer(customerid == null
                    ? null
                    : loadCustomerExt(customerid));

            ticket.setLines(new PreparedSentence(s, "SELECT L.TICKET, L.LINE, L.PRODUCT, L.ATTRIBUTESETINSTANCE_ID, L.UNITS, L.PRICE, T.ID, T.NAME, T.CATEGORY, T.CUSTCATEGORY, T.PARENTID, T.RATE, T.RATECASCADE, T.RATEORDER, L.ATTRIBUTES, L.REFUNDQTY  "
                    + "FROM TICKETLINES L, TAXES T WHERE L.TAXID = T.ID AND L.TICKET = ? ORDER BY L.LINE", SerializerWriteString.INSTANCE, new SerializerReadClass(TicketLineInfo.class)).list(ticket.getId()));
            ticket.setPayments(new PreparedSentence(s //                    , "SELECT PAYMENT, TOTAL, TRANSID TENDERED FROM PAYMENTS WHERE RECEIPT = ?" 
                    , "SELECT PAYMENT, TOTAL, TRANSID, TENDERED, CARDNAME FROM PAYMENTS WHERE RECEIPT = ?", SerializerWriteString.INSTANCE, new SerializerReadClass(PaymentInfoTicket.class)).list(ticket.getId()));
        }
        return ticket;
    }

    /**
     *
     * @param ticket
     * @param location
     * @throws BasicException
     */
    public final void saveTicket(final TicketInfo ticket, final String location) throws BasicException {

        Transaction t;
        t = new Transaction(s) {
            @Override
            public Object transact() throws BasicException {

                // Set Receipt Id
                switch (ticket.getTicketType()) {
                    case NORMAL:
                        if (ticket.getTicketId() == 0) {
                            ticket.setTicketId(getNextTicketIndex());
                        }
                        break;
                    case REFUND:
                        ticket.setTicketId(getNextTicketRefundIndex());
                        break;
                    case PAYMENT:
                        ticket.setTicketId(getNextTicketPaymentIndex());
                        break;
                    case NOSALE:
                        ticket.setTicketId(getNextTicketPaymentIndex());
                        break;
                    case INVOICE:
                        ticket.setTicketId(getNextTicketInvoiceIndex());
                        break;

                    default:
                        throw new BasicException();
                }

                new PreparedSentence(s, "INSERT INTO RECEIPTS (ID, MONEY, DATENEW, ATTRIBUTES, PERSON) VALUES (?, ?, ?, ?, ?)", SerializerWriteParams.INSTANCE
                ).exec(new DataParams() {
                    @Override
                    public void writeValues() throws BasicException {
                        setString(1, ticket.getId());
                        setString(2, ticket.getActiveCash());
                        setTimestamp(3, ticket.getDate());
                        try {
                            ByteArrayOutputStream o = new ByteArrayOutputStream();
                            ticket.getProperties().storeToXML(o, AppLocal.APP_NAME, "UTF-8");
                            setBytes(4, o.toByteArray());
                        } catch (IOException e) {
                            setBytes(4, null);
                        }
                        setString(5, ticket.getProperty("person"));
                    }
                }
                );

                // new ticket
                new PreparedSentence(s, "INSERT INTO TICKETS (ID, TICKETTYPE, TICKETID, PERSON, CUSTOMER) VALUES (?, ?, ?, ?, ?)", SerializerWriteParams.INSTANCE
                ).exec(new DataParams() {
                    @Override
                    public void writeValues() throws BasicException {
                        setString(1, ticket.getId());
                        setInt(2, ticket.getTicketType().getId());
                        setInt(3, ticket.getTicketId());
                        setString(4, ticket.getUser().getId());
                        setString(5, ticket.getCustomerId());
                    }
                }
                );

                SentenceExec ticketlineinsert = new PreparedSentence(s, "INSERT INTO TICKETLINES (TICKET, LINE, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, TAXID, ATTRIBUTES, REFUNDQTY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", SerializerWriteBuilder.INSTANCE);

                for (TicketLineInfo l : ticket.getLines()) {

                    ticketlineinsert.exec(l);

                    if (l.getProductID() != null && l.isProductService() != true
                            && l.getManageStock() == true) {

                        //     List<ProductsRecipeInfo> kit = getProductsKit(l.getTheatreID());
                        //     if (kit.size() == 0) {
                        // update the stock
                        getStockDiaryInsert().exec(new Object[]{
                            UUID.randomUUID().toString(),
                            ticket.getDate(),
                            l.getMultiply() < 0.0
                            ? MovementReason.IN_REFUND.getKey()
                            : MovementReason.OUT_SALE.getKey(),
                            location,
                            l.getProductID(),
                            l.getProductAttSetInstId(), -l.getMultiply(), l.getPrice(),
                            ticket.getUser().getName(),
                            null, null, null, null, null, null, null
                        });
                    }
                    /* else {
                            for (ProductsRecipeInfo component : kit) {
                                getStockDiaryInsert().exec(new Object[]{
                                    UUID.randomUUID().toString(),
                                    ticket.getDate(),
                                    component.getQuantity() < 0.0
                                    ? MovementReason.IN_RECIPES.getKey()
                                    : MovementReason.OUT_RECIPES.getKey(),
                                    location,
                                    component.getProductKitId(),
                                    null, -component.getQuantity() * l.getMultiply(), 0.0,
                                    ticket.getUser().getName(),
                                    null, null, null, null, null, null, null
                                });
                            }
                        }*/
                }
                // }

                final Payments payments = new Payments();
                SentenceExec paymentinsert = new PreparedSentence(s, "INSERT INTO PAYMENTS (ID, RECEIPT, PAYMENT, TOTAL, TRANSID, RETURNMSG, TENDERED, CARDNAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", SerializerWriteParams.INSTANCE);

                for (final PaymentInfo p : ticket.getPayments()) {
                    payments.addPayment(p.getName(), p.getTotal(), p.getPaid(), ticket.getReturnMessage());
                }

                //for (final PaymentInfo p : ticket.getPayments()) {
                while (payments.getSize() >= 1) {
                    paymentinsert.exec(new DataParams() {
                        @Override
                        public void writeValues() throws BasicException {
                            pName = payments.getFirstElement();
                            getTotal = payments.getPaidAmount(pName);
                            getTendered = payments.getTendered(pName);
                            getRetMsg = payments.getRtnMessage(pName);
                            payments.removeFirst(pName);

                            setString(1, UUID.randomUUID().toString());
                            setString(2, ticket.getId());
                            setString(3, pName);
                            setDouble(4, getTotal);
                            setString(5, ticket.getTransactionID());
                            setBytes(6, (byte[]) Formats.BYTEA.parseValue(getRetMsg));
                            setDouble(7, getTendered);
                            setString(8, getCardName);
                            payments.removeFirst(pName);
                        }
                    });

                    if ("debt".equals(pName) || "debtpaid".equals(pName)) {
                        // udate customer fields...
                        ticket.getCustomer().updateCurDebt(getTotal, ticket.getDate());
                        // save customer fields...
                        getDebtUpdate().exec(new DataParams() {
                            @Override
                            public void writeValues() throws BasicException {
                                setDouble(1, ticket.getCustomer().getCurdebt());
                                setTimestamp(2, ticket.getCustomer().getCurdate());
                                setString(3, ticket.getCustomer().getId());
                            }
                        });
                    }
                }

                SentenceExec taxlinesinsert = new PreparedSentence(s, "INSERT INTO TAXLINES (ID, RECEIPT, TAXID, BASE, AMOUNT)  VALUES (?, ?, ?, ?, ?)", SerializerWriteParams.INSTANCE);
                if (ticket.getTaxes() != null) {
                    for (final TicketTaxInfo tickettax : ticket.getTaxes()) {
                        taxlinesinsert.exec(new DataParams() {
                            @Override
                            public void writeValues() throws BasicException {
                                setString(1, UUID.randomUUID().toString());
                                setString(2, ticket.getId());
                                setString(3, tickettax.getTaxInfo().getId());
                                setDouble(4, tickettax.getSubTotal());
                                setDouble(5, tickettax.getTax());
                            }
                        });
                    }
                }

                return null;
            }
        };
        t.execute();
    }

    /**
     *
     * @param ticket
     * @param location
     * @throws BasicException
     */
    public final void deleteTicket(final TicketInfo ticket, final String location) throws BasicException {

        Transaction t = new Transaction(s) {
            @Override
            public Object transact() throws BasicException {

                // update the inventory
                Date d = new Date();
                for (int i = 0; i < ticket.getLinesCount(); i++) {
                    if (ticket.getLine(i).getProductID() != null
                            && ticket.getLine(i).getManageStock() == true) {

                        // Hay que actualizar el stock si el hay producto
                        getStockDiaryInsert().exec(new Object[]{
                            UUID.randomUUID().toString(),
                            d,
                            ticket.getLine(i).getMultiply() >= 0.0
                            ? MovementReason.IN_REFUND.getKey()
                            : MovementReason.OUT_SALE.getKey(),
                            location,
                            ticket.getLine(i).getProductID(),
                            ticket.getLine(i).getProductAttSetInstId(), ticket.getLine(i).getMultiply(), ticket.getLine(i).getPrice(),
                            ticket.getUser().getName()
                        });
                    }
                }

                // update customer debts
                for (PaymentInfo p : ticket.getPayments()) {
                    if ("debt".equals(p.getName()) || "debtpaid".equals(p.getName())) {

                        // udate customer fields...
                        ticket.getCustomer().updateCurDebt(-p.getTotal(), ticket.getDate());

                        // save customer fields...
                        getDebtUpdate().exec(new DataParams() {
                            @Override
                            public void writeValues() throws BasicException {
                                setDouble(1, ticket.getCustomer().getCurdebt());
                                setTimestamp(2, ticket.getCustomer().getCurdate());
                                setString(3, ticket.getCustomer().getId());
                            }
                        });
                    }
                }

                // and delete the receipt
                new StaticSentence(s, "DELETE FROM TAXLINES WHERE RECEIPT = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s, "DELETE FROM PAYMENTS WHERE RECEIPT = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s, "DELETE FROM TICKETLINES WHERE TICKET = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s, "DELETE FROM TICKETS WHERE ID = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                new StaticSentence(s, "DELETE FROM RECEIPTS WHERE ID = ?", SerializerWriteString.INSTANCE).exec(ticket.getId());
                return null;
            }
        };
        t.execute();
    }

    /**
     *
     * @return @throws BasicException
     */
    public final Integer getNextPickupIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "PICKUP_NUMBER").find();
    }

    /**
     *
     * @return @throws BasicException
     */
    public final Integer getNextTicketIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM").find();
    }

    public final Integer getNextTicketInvoiceIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM_INVOICE").find();
    }

    /**
     *
     * @return @throws BasicException
     */
    public final Integer getNextTicketRefundIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM_REFUND").find();
    }

    /**
     *
     * @return @throws BasicException
     */
    public final Integer getNextTicketPaymentIndex() throws BasicException {
        return (Integer) s.DB.getSequenceSentence(s, "TICKETSNUM_PAYMENT").find();
    }

    /**
     *
     * @return
     */
    public final SentenceList getProductCatQBF() {
        return new StaticSentence(s, new QBFBuilder(
                "SELECT "
                + getSelectFieldList()
                + "FROM PRODUCTS P "
                + "WHERE ?(QBF_FILTER) "
                + "ORDER BY P.REFERENCE",
                new String[]{
                    "P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.CATEGORY", "P.CODE"}, false), new SerializerWriteBasic(new Datas[]{
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.DOUBLE,
            Datas.OBJECT, Datas.STRING,
            Datas.OBJECT, Datas.STRING}), productsRow.getSerializerRead());
    }

    /**
     *
     * @return
     */
    public final SentenceExec getProductCatInsert() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                int i = new PreparedSentence(s, "INSERT INTO PRODUCTS (ID, "
                        + "REFERENCE, CODE, CODETYPE, NAME, ISCOM, "
                        + "ISSCALE, PRICEBUY, PRICESELL, CATEGORY, TAXCAT, "
                        + "ATTRIBUTESET_ID, IMAGE, STOCKCOST, STOCKVOLUME, ISCATALOG, CATORDER, "
                        + "ATTRIBUTES, ISKITCHEN, ISSERVICE, DISPLAY, ISVPRICE, "
                        + "ISVERPATRIB, TEXTTIP, WARRANTY, STOCKUNITS, ALIAS, ALWAYSAVAILABLE, DISCOUNTED, CANDISCOUNT, "
                        + "ISPACK, PACKQUANTITY, PACKPRODUCT, PROMOTIONID, MANAGESTOCK, ISBOXOFFICE  ) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        new SerializerWriteBasicExt(productsRow.getDatas(),
                                new int[]{INDEX_ID,
                                    INDEX_REFERENCE, INDEX_CODE, INDEX_CODETYPE,
                                    INDEX_NAME, INDEX_ISCOM, INDEX_ISSCALE,
                                    INDEX_PRICEBUY, INDEX_PRICESELL, INDEX_CATEGORY,
                                    INDEX_TAXCAT, INDEX_ATTRIBUTESET_ID, INDEX_IMAGE,
                                    INDEX_STOCKCOST, INDEX_STOCKVOLUME,
                                    INDEX_ISCATALOG, INDEX_CATORDER, INDEX_ATTRIBUTES,
                                    INDEX_ISKITCHEN, INDEX_ISSERVICE, INDEX_DISPLAY,
                                    INDEX_ISVPRICE, INDEX_ISVERPATRIB, INDEX_TEXTTIP,
                                    INDEX_WARRANTY, INDEX_STOCKUNITS, INDEX_ALIAS,
                                    INDEX_ALWAYSAVAILABLE, INDEX_DISCOUNTED, INDEX_CANDISCOUNT,
                                    INDEX_ISPACK, INDEX_PACKQUANTITY, INDEX_PACKPRODUCT,
                                    INDEX_PROMOTIONID, INDEX_MANAGESTOCK, INDEX_ISBOXOFFICE})).exec(params);

                new PreparedSentence(s, "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, UNITS) VALUES ('0', ?, 0.0)",
                        new SerializerWriteBasicExt(productsRow.getDatas(), new int[]{INDEX_ID})).exec(params);

                return i;
            }
        };
    }

    public final SentenceList getPackProductList() {
        return new StaticSentence(s, "SELECT "
                + "ID, "
                + "NAME "
                + "FROM PRODUCTS "
                + "ORDER BY NAME", null, new SerializerRead() {
            @Override
            public Object readValues(DataRead dr) throws BasicException {
                return new PackProductInfo(dr.getString(1), dr.getString(2));
            }
        });
    }

    /**
     *
     * @return
     */
    public final SentenceExec getProductCatUpdate() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                int i = new PreparedSentence(s, "UPDATE PRODUCTS SET REFERENCE = ?, "
                        + "CODE = ?, CODETYPE = ?, NAME = ?, ISCOM = ?, "
                        + "ISSCALE = ?, PRICEBUY = ?, "
                        + "PRICESELL = ?, CATEGORY = ?, "
                        + "TAXCAT = ?, ATTRIBUTESET_ID = ?, "
                        + "IMAGE = ?, STOCKCOST = ?, "
                        + "STOCKVOLUME = ?, ATTRIBUTES = ?, "
                        + "ISKITCHEN = ?, ISSERVICE = ?, "
                        + "DISPLAY = ?, ISVPRICE = ?, "
                        + "ISVERPATRIB = ?, TEXTTIP = ?, "
                        + "WARRANTY = ?, STOCKUNITS = ?, ALIAS = ?, ALWAYSAVAILABLE = ?, "
                        + "DISCOUNTED = ?, CANDISCOUNT = ?, "
                        + "ISPACK = ?, PACKQUANTITY = ?, PACKPRODUCT = ?, "
                        + "PROMOTIONID = ?, ISCATALOG = ?, CATORDER = ?, "
                        + "MANAGESTOCK = ?, ISBOXOFFICE = ? "
                        + "WHERE ID = ?", new SerializerWriteBasicExt(productsRow.getDatas(),
                                new int[]{
                                    INDEX_REFERENCE, INDEX_CODE, INDEX_CODETYPE,
                                    INDEX_NAME, INDEX_ISCOM, INDEX_ISSCALE,
                                    INDEX_PRICEBUY, INDEX_PRICESELL, INDEX_CATEGORY,
                                    INDEX_TAXCAT, INDEX_ATTRIBUTESET_ID, INDEX_IMAGE,
                                    INDEX_STOCKCOST, INDEX_STOCKVOLUME, INDEX_ATTRIBUTES,
                                    INDEX_ISKITCHEN, INDEX_ISSERVICE, INDEX_DISPLAY,
                                    INDEX_ISVPRICE, INDEX_ISVERPATRIB, INDEX_TEXTTIP,
                                    INDEX_WARRANTY, INDEX_STOCKUNITS, INDEX_ALIAS,
                                    INDEX_ALWAYSAVAILABLE, INDEX_DISCOUNTED, INDEX_CANDISCOUNT,
                                    INDEX_ISPACK, INDEX_PACKQUANTITY, INDEX_PACKPRODUCT,
                                    INDEX_PROMOTIONID, INDEX_ISCATALOG, INDEX_CATORDER,
                                    INDEX_MANAGESTOCK, INDEX_ISBOXOFFICE,
                                    INDEX_ID})).exec(params);

                return i;
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getProductCatDelete() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s, "DELETE FROM STOCKCURRENT WHERE PRODUCT = ?", new SerializerWriteBasicExt(productsRow.getDatas(), new int[]{INDEX_ID})).exec(params);
                return new PreparedSentence(s, "DELETE FROM PRODUCTS WHERE ID = ?", new SerializerWriteBasicExt(productsRow.getDatas(), new int[]{INDEX_ID})).exec(params);

            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getDebtUpdate() {
        return new PreparedSentence(s, "UPDATE CUSTOMERS SET CURDEBT = ?, CURDATE = ? WHERE ID = ?", SerializerWriteParams.INSTANCE);
    }

    /**
     *
     * @return
     */
    public final SentenceExec getStockDiaryInsert() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                /* Set up adjust parameters */
                Object[] adjustParams = new Object[4];
                Object[] paramsArray = (Object[]) params;
                adjustParams[0] = paramsArray[3]; //product ->Location
                adjustParams[1] = paramsArray[4]; //location -> Product
                adjustParams[2] = paramsArray[5]; //attrubutesetinstance
                adjustParams[3] = paramsArray[6]; //units
                int as = adjustStock(adjustParams);

                return as + new PreparedSentence(s, "INSERT INTO STOCKDIARY (ID, DATENEW, REASON, LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, AppUser) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasicExt(stockdiaryDatas, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8})).exec(params);
            }
        };
    }

    /*
    public final SentenceExec getStockDiaryInsert() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                int updateresult = ((Object[]) params)[5] == null // si ATTRIBUTESETINSTANCE_ID is null
                        ? new PreparedSentence(s, "UPDATE STOCKCURRENT SET UNITS = (UNITS + ?) "
                                + "WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL", new SerializerWriteBasicExt(stockdiaryDatas, new int[]{6, 3, 4})).exec(params)
                        : new PreparedSentence(s, "UPDATE STOCKCURRENT SET UNITS = (UNITS + ?) "
                                + "WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?", new SerializerWriteBasicExt(stockdiaryDatas, new int[]{6, 3, 4, 5})).exec(params);

                if (updateresult == 0) {
                    new PreparedSentence(s, "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) "
                            + "VALUES (?, ?, ?, ?)", new SerializerWriteBasicExt(stockdiaryDatas, new int[]{3, 4, 5, 6})).exec(params);
                }
                return new PreparedSentence(s, "INSERT INTO STOCKDIARY (ID, DATENEW, REASON, LOCATION, PRODUCT, "
                        + "ATTRIBUTESETINSTANCE_ID, UNITS, PRICE, AppUser) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", new SerializerWriteBasicExt(stockdiaryDatas,
                                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8})).exec(params);
            }
        };
    }
     */
    /**
     *
     * @return
     */
    public final SentenceExec getStockDiaryDelete() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                int updateresult = ((Object[]) params)[5] == null // if ATTRIBUTESETINSTANCE_ID is null
                        ? new PreparedSentence(s, "UPDATE STOCKCURRENT SET UNITS = (UNITS - ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL", new SerializerWriteBasicExt(stockdiaryDatas, new int[]{6, 3, 4})).exec(params)
                        : new PreparedSentence(s, "UPDATE STOCKCURRENT SET UNITS = (UNITS - ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?", new SerializerWriteBasicExt(stockdiaryDatas, new int[]{6, 3, 4, 5})).exec(params);

                if (updateresult == 0) {
                    new PreparedSentence(s, "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, -(?))", new SerializerWriteBasicExt(stockdiaryDatas, new int[]{3, 4, 5, 6})).exec(params);
                }
                return new PreparedSentence(s, "DELETE FROM STOCKDIARY WHERE ID = ?", new SerializerWriteBasicExt(stockdiaryDatas, new int[]{0})).exec(params);
            }
        };
    }
    
    
 
    
    
    /**
     *
     * @return @throws BasicException
     */
    public final List<ShowSalesInfo> getShowsForDate(Date forDate) throws BasicException {
        
        DateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");        
        
        String dateFormat = dbDateFormat.format(forDate);
        
        return new PreparedSentence(
                s, 
                "SELECT "
                        + " S.ID, "
                        + " S.THEATREID, "
                        + " S.STARTDATE, "
                        + " S.ENDDATE, "
                        + " S.REPORTSTARTDATE, "
                        + " S.REPORTENDDATE, "
                        + " T.NAME "
                        + "FROM "
                        + " SHOWS S"
                        + " INNER JOIN THEATRES T ON S.THEATREID = T.ID "                        
                        + "WHERE "
                        + " S.STARTDATE <= '" + dateFormat + "' AND S.ENDDATE >= '" + dateFormat + "' "
                        + "ORDER BY T.NAME, S.STARTDATE", 
                null, 
                ShowSalesInfo.getSerializerRead()
        ).list(dateFormat, dateFormat);
    }
    
    
    
    /**
     *
     * @return @throws BasicException
     */
    public final List<ShowFeaturesInfo> getFeaturesForShow(String showID) throws BasicException {
        return new PreparedSentence(
                s, 
                "SELECT "
                        + " SF.ID, "
                        + " SF.SHOWID, "
                        + " SF.FEATUREID, "
                        + " SF.SEQUENCE, "
                        + " SF.STARTTIME, "
                        + " F.NAME, "
                        + " F.IMAGE, "
                        + " F.RUNTIME, "
                        + " F.RATINGID, "
                        + " F.ACTIVE, "
                        + " R.NAME "
                        + "FROM "
                        + " SHOWFEATURES SF "
                        + " INNER JOIN FEATURES F ON SF.FEATUREID = F.ID "
                        + " INNER JOIN RATINGS R ON F.RATINGID = R.ID "
                        + "WHERE "
                        + " SF.SHOWID = ? "
                        + "ORDER BY SF.SEQUENCE, SF.STARTTIME", 
                SerializerWriteString.INSTANCE, 
                ShowFeaturesInfo.getSerializerRead()
        ).list(showID);
    }
    
    
    
    

    /**
     *
     * @return
     */
    public final SentenceList getFeaturesQBF() {
        return new StaticSentence(s, 
            new QBFBuilder(
                "SELECT "
                + "F.ID, "
                + "F.NAME, "
                + "F.IMAGE, "
                + "F.RUNTIME, "
                + "F.RATINGID, "
                + "F.ACTIVE "
                + "FROM FEATURES F "
                + "WHERE ?(QBF_FILTER) "
                + "ORDER BY F.NAME",
                new String[] {
                    "F.NAME", 
                    "F.ACTIVE"
                }, 
                false
            ), 
            new SerializerWriteBasic(
                new Datas[]{
                    Datas.OBJECT, Datas.STRING,
                    Datas.OBJECT, Datas.BOOLEAN
                }
            ), 
            featuresRow.getSerializerRead()
        );
    }
    
    /**
     *
     * @return
     */
    public final SentenceList getFeaturesList() {
        return new StaticSentence(
                s,
                "SELECT "
                + "F.ID, "
                + "F.NAME, "
                + "F.IMAGE, "
                + "F.RUNTIME, "
                + "F.RATINGID, "
                + "F.ACTIVE "
                + "FROM FEATURES F "
                + "WHERE F.ACTIVE = TRUE "
                + "ORDER BY F.NAME",
                null,
                FeatureInfo.getSerializerRead()
        );
    }
    

    /**
     *
     * @return
     */
    public final SentenceExec getFeatureInsert() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                return new PreparedSentence(
                            s, 
                            "INSERT INTO FEATURES (ID, NAME, IMAGE, RUNTIME, RATINGID, ACTIVE) VALUES (?, ?, ?, ?, ?, ?)",
                            new SerializerWriteBasicExt(
                                featuresRow.getDatas(),
                                new int[] {
                                    INDEX_FEATURE_ID, 
                                    INDEX_FEATURE_NAME, 
                                    INDEX_FEATURE_IMAGE, 
                                    INDEX_FEATURE_RUNTIME, 
                                    INDEX_FEATURE_RATINGID, 
                                    INDEX_FEATURE_ACTIVE
                                }
                            )
                ).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getFeatureUpdate() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                return new PreparedSentence(s, 
                        "UPDATE FEATURES "
                        + "SET "
                        + "NAME = ?, "
                        + "IMAGE = ?, "
                        + "RUNTIME = ?, "
                        + "RATINGID = ?, "
                        + "ACTIVE = ? "
                        + "WHERE ID = ?", 
                        new SerializerWriteBasicExt(
                                featuresRow.getDatas(),
                                new int[]{
                                    INDEX_FEATURE_NAME, 
                                    INDEX_FEATURE_IMAGE, 
                                    INDEX_FEATURE_RUNTIME, 
                                    INDEX_FEATURE_RATINGID, 
                                    INDEX_FEATURE_ACTIVE,
                                    INDEX_FEATURE_ID
                                }
                        )
                ).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getFeatureDelete() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                return new PreparedSentence(s, "DELETE FROM FEATURES WHERE ID = ?", new SerializerWriteBasicExt(featuresRow.getDatas(), new int[]{INDEX_FEATURE_ID})).exec(params);
            }
        };
    }
    

    public final SentenceList getShowsTheatresList() {
        return new StaticSentence(
                s,
                "SELECT "
                + " S.ID,"
                + " T.NAME, "
                + " S.STARTDATE, "
                + " S.ENDDATE "
                + "FROM "
                + " SHOWS S "
                + " INNER JOIN THEATRES T ON S.THEATREID = T.ID "
                + "WHERE "
                + " S.STARTDATE >= CURRENT_DATE "
                + "ORDER BY"
                + " T.NAME, "
                + " S.STARTDATE, "
                + " S.ENDDATE", 
                null,
                ShowTheatreInfo.getSerializerRead()
        );
    }
    
    
    

    /**
     *
     * @return
     */
    public final SentenceList getShowFeaturesQBF() {
        return new StaticSentence(s,
            new QBFBuilder(
                "SELECT "
                + "SF.ID, "
                + "SF.SHOWID, "
                + "SF.FEATUREID, "
                + "F.NAME, "
                + "SF.SEQUENCE, "
                + "SF.STARTTIME "
                + "FROM SHOWFEATURES SF INNER JOIN FEATURES F ON SF.FEATUREID = F.ID "
                + "WHERE ?(QBF_FILTER) "
                + "ORDER BY SF.SEQUENCE, F.NAME ",
                new String[] {
                    "SF.SHOWID"
                },
                false
            ),
            new SerializerWriteBasic(
                new Datas[]{
                    Datas.OBJECT, Datas.STRING
                }
            ),
            showFeaturesQBFRow.getSerializerRead()
        );
    }
    
    

    /**
     *
     * @return
     */
    public final SentenceExec getShowFeaturesInsert() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                return new PreparedSentence(
                    s,
                    "INSERT INTO SHOWFEATURES (ID, SHOWID, FEATUREID, SEQUENCE, STARTTIME) VALUES (?, ?, ?, ?, ?)",
                    new SerializerWriteBasicExt(
                        showFeaturesRow.getDatas(),
                        new int[] {
                            INDEX_SHOW_FEATURES_ID,
                            INDEX_SHOW_FEATURES_SHOWID,
                            INDEX_SHOW_FEATURES_FEATUREID,
                            INDEX_SHOW_FEATURES_SEQUENCE,
                            INDEX_SHOW_FEATURES_STARTTIME
                        }
                    )
                ).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getShowFeaturesUpdate() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                return new PreparedSentence(s,
                        "UPDATE SHOWFEATURES "
                        + "SET "
                        + "SHOWID = ?, "
                        + "FEATUREID = ?, "
                        + "SEQUENCE = ?, "
                        + "STARTTIME = ? "
                        + "WHERE ID = ?",
                        new SerializerWriteBasicExt(
                                showFeaturesRow.getDatas(),
                                new int[]{
                                    INDEX_SHOW_FEATURES_SHOWID,
                                    INDEX_SHOW_FEATURES_FEATUREID,
                                    INDEX_SHOW_FEATURES_SEQUENCE,
                                    INDEX_SHOW_FEATURES_STARTTIME,
                                    INDEX_SHOW_FEATURES_ID
                                }
                        )
                ).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getShowFeaturesDelete() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                return new PreparedSentence(s, 
                        "DELETE FROM SHOWFEATURES WHERE ID = ?", 
                        new SerializerWriteBasicExt(
                                showFeaturesRow.getDatas(), 
                                new int[]{
                                    INDEX_SHOW_FEATURES_ID
                                }
                        )
                ).exec(params);
            }
        };
    }

    
    
    
    
    /**
     *
     * @return
     */
    public final SentenceList getTheatresList() {
        return new StaticSentence(
                s,
                "SELECT "
                + "T.ID, "
                + "T.NAME, "
                + "T.CAPACITYMODE, "
                + "T.CAPACITY, "
                + "T.HARDLIMIT, "
                + "T.ACTIVE "
                + "FROM THEATRES T "
                + "WHERE T.ACTIVE = TRUE "
                + "ORDER BY T.NAME",
                null,
                TheatreInfo.getSerializerRead()
        );
    }



    /**
     *
     * @return
     */
    public final SentenceList getRatingsQBF() {
        return new StaticSentence(s,
            new QBFBuilder(
                "SELECT "
                + "R.ID, "
                + "R.NAME, "
                + "R.ACTIVE "
                + "FROM RATINGS R "
                + "WHERE ?(QBF_FILTER) "
                + "ORDER BY R.NAME ",
                new String[] {
                    "R.NAME",
                    "R.ACTIVE"
                },
                false
            ),
            new SerializerWriteBasic(
                new Datas[]{
                    Datas.OBJECT, Datas.STRING,
                    Datas.OBJECT, Datas.BOOLEAN
                }
            ),
            ratingsRow.getSerializerRead()
        );
    }
    
    
    /**
     *
     * @return
     */
    public final SentenceList getRatingsList() {
        return new StaticSentence(
                s,
                "SELECT "
                + "R.ID, "
                + "R.NAME, "
                + "R.ACTIVE "
                + "FROM RATINGS R "
                + "WHERE R.ACTIVE = TRUE "
                + "ORDER BY R.NAME",
                null,
                RatingInfo.getSerializerRead()
        );
    }

    

    /**
     *
     * @return
     */
    public final SentenceExec getRatingInsert() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                return new PreparedSentence(
                    s,
                    "INSERT INTO RATINGS (ID, NAME, ACTIVE) VALUES (?, ?, ?)",
                    new SerializerWriteBasicExt(
                        ratingsRow.getDatas(),
                        new int[] {
                            INDEX_RATING_ID,
                            INDEX_RATING_NAME,
                            INDEX_RATING_ACTIVE
                        }
                    )
                ).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getRatingUpdate() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                return new PreparedSentence(s,
                        "UPDATE RATINGS "
                        + "SET "
                        + "NAME = ?, "
                        + "ACTIVE = ? "
                        + "WHERE ID = ?",
                        new SerializerWriteBasicExt(
                                ratingsRow.getDatas(),
                                new int[]{
                                    INDEX_RATING_NAME,
                                    INDEX_RATING_ACTIVE,
                                    INDEX_RATING_ID
                                }
                        )
                ).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getRatingDelete() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                return new PreparedSentence(s, 
                        "DELETE FROM RATINGS WHERE ID = ?", 
                        new SerializerWriteBasicExt(
                                ratingsRow.getDatas(), 
                                new int[]{
                                    INDEX_RATING_ID
                                }
                        )
                ).exec(params);
            }
        };
    }

    
    

    /**
     *
     * @return
     */
    public final SentenceList getShowQBF() {
        return new StaticSentence(s,
            new QBFBuilder(
                "SELECT "
                + "S.ID, "
                + "S.THEATREID, "
                + "S.STARTDATE, "
                + "S.ENDDATE, "
                + "S.REPORTSTARTDATE, "
                + "S.REPORTENDDATE "
                + "FROM SHOWS S "
                + "WHERE ?(QBF_FILTER) "
                + "ORDER BY S.STARTDATE",
                new String[] {
                    "S.THEATREID",
                    "S.ENDDATE"
                },
                false
            ),
            new SerializerWriteBasic(
                new Datas[]{
                    Datas.OBJECT, Datas.STRING,
                    Datas.OBJECT, Datas.DATE
                }
            ),
            showsRow.getSerializerRead()
        );
    }






    /**
     *
     * @return
     */
    public final SentenceExec getShowInsert() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                return new PreparedSentence(
                    s,
                    "INSERT INTO SHOWS (ID, THEATREID, STARTDATE, ENDDATE, REPORTSTARTDATE, REPORTENDDATE) VALUES (?, ?, ?, ?, ?, ?)",
                    new SerializerWriteBasicExt(
                        showsRow.getDatas(),
                        new int[] {
                            INDEX_SHOW_ID,
                            INDEX_SHOW_THEATREID,
                            INDEX_SHOW_STARTDATE,
                            INDEX_SHOW_ENDDATE,
                            INDEX_SHOW_REPORTSTARTDATE,
                            INDEX_SHOW_REPORTENDDATE
                        }
                    )
                ).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getShowUpdate() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                Object[] values = (Object[]) params;
                return new PreparedSentence(s,
                        "UPDATE SHOWS "
                        + "SET "
                        + "THEATREID = ?, "
                        + "STARTDATE = ?, "
                        + "ENDDATE = ?, "
                        + "REPORTSTARTDATE = ?, "
                        + "REPORTENDDATE = ? "
                        + "WHERE ID = ?",
                        new SerializerWriteBasicExt(
                                showsRow.getDatas(),
                                new int[]{
                                    INDEX_SHOW_THEATREID,
                                    INDEX_SHOW_STARTDATE,
                                    INDEX_SHOW_ENDDATE,
                                    INDEX_SHOW_REPORTSTARTDATE,
                                    INDEX_SHOW_REPORTENDDATE,
                                    INDEX_SHOW_ID
                                }
                        )
                ).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getShowDelete() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                return new PreparedSentence(s, 
                        "DELETE FROM SHOWS WHERE ID = ?", 
                        new SerializerWriteBasicExt(
                                showsRow.getDatas(), 
                                new int[]{
                                    INDEX_SHOW_ID
                                }
                        )
                ).exec(params);
            }
        };
    }

    

    /**
     * @param params[0] Product ID
     * @param params[1] location Location to adjust from
     * @param params[2] Attribute ID
     * @param params[3] Units
     */
    private int adjustStock(Object params[]) throws BasicException {
        /* Retrieve product kit */
        List<ProductsRecipeInfo> kit = getProductsKit((String) ((Object[]) params)[1]);
        if (kit.size() > 0) {
            /* If this is a kit, i.e. has hits, call recursively for each product */
            int as = 0;
            for (ProductsRecipeInfo component : kit) {
                Object[] adjustParams = new Object[4];
                adjustParams[0] = params[0];
                adjustParams[1] = component.getProductKitId();
                adjustParams[2] = params[2];
                adjustParams[3] = ((Double) params[3]) * component.getQuantity();
                as += adjustStock(adjustParams);
            }
            return as;
        } else {
            /* If not, adjust the stock */
            int updateresult = ((Object[]) params)[2] == null // si ATTRIBUTESETINSTANCE_ID is null
                    ? new PreparedSentence(s, "UPDATE STOCKCURRENT SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL", new SerializerWriteBasicExt(stockAdjustDatas, new int[]{3, 0, 1})).exec(params)
                    : new PreparedSentence(s, "UPDATE STOCKCURRENT SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?", new SerializerWriteBasicExt(stockAdjustDatas, new int[]{3, 0, 1, 2})).exec(params);

            if (updateresult == 0) {
                new PreparedSentence(s, "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, ATTRIBUTESETINSTANCE_ID, UNITS) VALUES (?, ?, ?, ?)", new SerializerWriteBasicExt(stockAdjustDatas, new int[]{0, 1, 2, 3})).exec(params);
            }
            return 1;
        }
    }

    public void addProductListItem(String listName, String ProductID) throws BasicException {
        new PreparedSentence(s, "INSERT INTO PRODUCTLISTS (LISTNAME, PRODUCT) VALUES ('"
                + listName + "','" + ProductID + "')", null).exec();
    }

    /**
     *
     * @return
     */
    public void removeProductListItem(String listName, String ProductID) throws BasicException {
        new PreparedSentence(s, "DELETE FROM PRODUCTLISTS WHERE LISTNAME ='"
                + listName + "' AND PRODUCT = '" + ProductID + "'", null).exec();
    }

    /**
     *
     * @return
     */
    public void removeProductList(String listName) throws BasicException {
        new PreparedSentence(s, "DELETE FROM PRODUCTLISTS WHERE LISTNAME ='"
                + listName + "'", null).exec();
    }

    /**
     *
     * @return
     */
    public final SentenceExec getPaymentMovementInsert() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s, "INSERT INTO RECEIPTS (ID, MONEY, DATENEW) VALUES (?, ?, ?)", new SerializerWriteBasicExt(paymenttabledatas, new int[]{0, 1, 2})).exec(params);
                return new PreparedSentence(s, "INSERT INTO PAYMENTS (ID, RECEIPT, PAYMENT, TOTAL, NOTES) VALUES (?, ?, ?, ?, ?)", new SerializerWriteBasicExt(paymenttabledatas, new int[]{3, 0, 4, 5, 6})).exec(params);
            }
        };
    }

    /**
     *
     * @return
     */
    public final SentenceExec getPaymentMovementDelete() {
        return new SentenceExecTransaction(s) {
            @Override
            public int execInTransaction(Object params) throws BasicException {
                new PreparedSentence(s, "DELETE FROM PAYMENTS WHERE ID = ?", new SerializerWriteBasicExt(paymenttabledatas, new int[]{3})).exec(params);
                return new PreparedSentence(s, "DELETE FROM RECEIPTS WHERE ID = ?", new SerializerWriteBasicExt(paymenttabledatas, new int[]{0})).exec(params);
            }
        };
    }

    public final Double getCustomerDebt(String id) throws BasicException {
        return (Double) new PreparedSentence(s, "SELECT CURDEBT FROM CUSTOMERS WHERE ID = ? ",
                SerializerWriteString.INSTANCE, SerializerReadDouble.INSTANCE).find(id);

    }

    /**
     *
     * @param warehouse
     * @param id
     * @param attsetinstid
     * @return
     * @throws BasicException
     */
    public final double findProductStock(String warehouse, String id, String attsetinstid) throws BasicException {
        PreparedSentence p = attsetinstid == null
                ? new PreparedSentence(s, "SELECT UNITS FROM STOCKCURRENT WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID IS NULL", new SerializerWriteBasic(Datas.STRING, Datas.STRING), SerializerReadDouble.INSTANCE)
                : new PreparedSentence(s, "SELECT UNITS FROM STOCKCURRENT WHERE LOCATION = ? AND PRODUCT = ? AND ATTRIBUTESETINSTANCE_ID = ?", new SerializerWriteBasic(Datas.STRING, Datas.STRING, Datas.STRING), SerializerReadDouble.INSTANCE);
        Double d = (Double) p.find(warehouse, id, attsetinstid);
        return d == null ? 0.0 : d;
    }

    public final double findProductStockSecurity(String warehouse, String id) throws BasicException {
        PreparedSentence p = new PreparedSentence(s, "SELECT STOCKSECURITY FROM STOCKLEVEL WHERE LOCATION = ? AND PRODUCT = ? ", new SerializerWriteBasic(Datas.STRING, Datas.STRING), SerializerReadDouble.INSTANCE);
        Double d = (Double) p.find(warehouse, id);
        return d == null ? 0.0 : d;
    }

    public final double findProductStockMaximum(String warehouse, String id) throws BasicException {
        PreparedSentence p = new PreparedSentence(s, "SELECT STOCKMAXIMUM FROM STOCKLEVEL WHERE LOCATION = ? AND PRODUCT = ? ", new SerializerWriteBasic(Datas.STRING, Datas.STRING), SerializerReadDouble.INSTANCE);
        Double d = (Double) p.find(warehouse, id);
        return d == null ? 0.0 : d;
    }

    /**
     *
     * @return
     */
    public final SentenceExec getCatalogCategoryAdd() {
        return new StaticSentence(s, "UPDATE PRODUCTS SET ISCATALOG = " + s.DB.TRUE() + " WHERE CATEGORY = ?", SerializerWriteString.INSTANCE);
    }

    /**
     *
     * @return
     */
    public final SentenceExec getCatalogCategoryDel() {
        return new StaticSentence(s, "UPDATE PRODUCTS SET ISCATALOG = " + s.DB.FALSE() + " WHERE CATEGORY = ?", SerializerWriteString.INSTANCE);
    }

    /**
     *
     * @return
     */
    public final TableDefinition getTableCategories() {
        return new TableDefinition(s,
                "CATEGORIES", new String[]{"ID", "NAME", "PARENTID", "IMAGE",
                    "TEXTTIP", "CATSHOWNAME", "COLOUR", "CATORDER"},
                new String[]{"ID", AppLocal.getIntString("Label.Name"), "",
                    AppLocal.getIntString("label.image")}, new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.IMAGE, Datas.STRING, Datas.BOOLEAN, Datas.STRING, Datas.INT}, new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.NULL, Formats.STRING, Formats.BOOLEAN, Formats.STRING, Formats.INT}, new int[]{0}
        );
    }

    /**
     *
     * @return
     */
    public final TableDefinition getTableTaxes() {
        return new TableDefinition(s,
                "TAXES", new String[]{"ID", "NAME", "CATEGORY", "CUSTCATEGORY", "PARENTID", "RATE", "RATECASCADE", "RATEORDER"},
                new String[]{"ID", AppLocal.getIntString("Label.Name"), AppLocal.getIntString("label.taxcategory"),
                    AppLocal.getIntString("label.custtaxcategory"), AppLocal.getIntString("label.taxparent"), AppLocal.getIntString("label.dutyrate"),
                    AppLocal.getIntString("label.cascade"), AppLocal.getIntString("label.order")},
                new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.BOOLEAN, Datas.INT},
                new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.STRING, Formats.PERCENT, Formats.BOOLEAN, Formats.INT},
                new int[]{0},
                "NAME"
        );
    }

    /**
     *
     * @return
     */
    public final TableDefinition getTableTaxCustCategories() {
        return new TableDefinition(s,
                "TAXCUSTCATEGORIES", new String[]{"ID", "NAME"},
                new String[]{"ID", AppLocal.getIntString("Label.Name")},
                new Datas[]{Datas.STRING, Datas.STRING},
                new Formats[]{Formats.STRING, Formats.STRING},
                new int[]{0},
                "NAME"
        );
    }

    /**
     *
     * @return
     */
    public final TableDefinition getTableTaxCategories() {
        return new TableDefinition(s,
                "TAXCATEGORIES", new String[]{"ID", "NAME"},
                new String[]{"ID", AppLocal.getIntString("Label.Name")},
                new Datas[]{Datas.STRING, Datas.STRING},
                new Formats[]{Formats.STRING, Formats.STRING},
                new int[]{0},
                "NAME"
        );
    }

    /**
     *
     * @return
     */
    public final TableDefinition getTableLocations() {
        return new TableDefinition(s,
                "LOCATIONS", new String[]{"ID", "NAME", "ADDRESS"},
                new String[]{"ID", AppLocal.getIntString("label.locationname"), AppLocal.getIntString("label.locationaddress")},
                new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING},
                new Formats[]{Formats.STRING, Formats.STRING, Formats.STRING},
                new int[]{0},
                "NAME"
        );
    }

    

    public final TableDefinition getTableFeatures() {
        return new TableDefinition(
                s,
                "SHOWS",
                new String[]{"ID", "NAME", "IMAGE", "RUNTIME", "RATINGID", "ACTIVE"},
                new String[]{"ID", AppLocal.getIntString("label.featurename"), AppLocal.getIntString("label.featureimage"), AppLocal.getIntString("label.featureruntime"), AppLocal.getIntString("label.featureratingid"), AppLocal.getIntString("label.featureactive")},
                new Datas[]{Datas.STRING, Datas.STRING, Datas.IMAGE, Datas.INT, Datas.STRING, Datas.BOOLEAN },
                new Formats[]{Formats.STRING, Formats.STRING, Formats.NULL, Formats.INT, Formats.STRING, Formats.BOOLEAN },
                new int[]{0},
                "NAME"
        );
    }
    

    public final TableDefinition getTableShows() {
        return new TableDefinition(
                s,
                "SHOWS",
                new String[]{"ID", "THEATREID", "STARTDATE", "ENDDATE", "REPORTSTARTDATE", "REPORTENDDATE"},
                new String[]{"ID", AppLocal.getIntString("label.theatreid"), AppLocal.getIntString("label.startdate"), AppLocal.getIntString("label.enddate"), AppLocal.getIntString("label.reportstartdate"), AppLocal.getIntString("label.reportenddate") },
                new Datas[]{Datas.STRING, Datas.STRING, Datas.DATE, Datas.DATE, Datas.DATE, Datas.DATE },
                new Formats[]{Formats.STRING, Formats.STRING, Formats.DATE, Formats.DATE, Formats.DATE, Formats.DATE },
                new int[]{0},
                "THEATREID, STARTDATE, ENDDATE"
        );
    }
    
    
    public final TableDefinition getTableRatings() {
        return new TableDefinition(
                s,
                "RATINGS",
                new String[]{"ID", "NAME", "ACTIVE"},
                new String[]{"ID", AppLocal.getIntString("label.ratingname"), AppLocal.getIntString("label.ratingactive")},
                new Datas[]{Datas.STRING, Datas.STRING, Datas.BOOLEAN},
                new Formats[]{Formats.STRING, Formats.STRING, Formats.BOOLEAN},
                new int[]{0},
                "NAME"
        );
    }
    
    
    public final void updateRefundQty(Double qty, String ticket, Integer line) throws BasicException {
        m_updateRefund.exec(qty, ticket, line);
    }

    public final boolean getVoucher(String id) throws BasicException {
        return new PreparedSentence(s,
                "SELECT SOLDTICKETID FROM VOUCHERS WHERE VOUCHER = ?",
                SerializerWriteString.INSTANCE,
                SerializerReadString.INSTANCE).find(id)
                != null;
    }

    public final String getProductNameByCode(String sCode) throws BasicException {
        return (String) m_productname.find(sCode);
    }

    public final void sellVoucher(Object[] voucher) throws BasicException {
        m_sellvoucher.exec(voucher);
    }

    public final void insertCategory(Object[] voucher) throws BasicException {
        m_insertcat.exec(voucher);
    }

    /**
     *
     */
    protected static class CustomerExtRead implements SerializerRead {

        /**
         *
         * @param dr
         * @return
         * @throws BasicException
         */
        @Override
        public Object readValues(DataRead dr) throws BasicException {
            CustomerInfoExt c = new CustomerInfoExt(dr.getString(1));
            c.setTaxid(dr.getString(2));
            c.setSearchkey(dr.getString(3));
            c.setName(dr.getString(4));
            c.setCard(dr.getString(5));
            c.setTaxCustomerID(dr.getString(6));
            c.setNotes(dr.getString(7));
            c.setMaxdebt(dr.getDouble(8));
            c.setVisible(dr.getBoolean(9));
            c.setCurdate(dr.getTimestamp(10));
            c.setCurdebt(dr.getDouble(11));
            c.setFirstname(dr.getString(12));
            c.setLastname(dr.getString(13));
            c.setEmail(dr.getString(14));
            c.setPhone(dr.getString(15));
            c.setPhone2(dr.getString(16));
            c.setFax(dr.getString(17));
            c.setAddress(dr.getString(18));
            c.setAddress2(dr.getString(19));
            c.setPostal(dr.getString(20));
            c.setCity(dr.getString(21));
            c.setRegion(dr.getString(22));
            c.setCountry(dr.getString(23));
            c.setImage(dr.getString(24));
            c.setDoB(dr.getTimestamp(25));
            c.setDiscount(dr.getDouble(26));

            return c;
        }
    }
}
