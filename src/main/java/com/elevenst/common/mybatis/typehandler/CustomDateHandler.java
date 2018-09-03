package com.elevenst.common.mybatis.typehandler;

import com.elevenst.common.util.StringUtil;
import com.elevenst.terroir.product.registration.product.exception.ProductException;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class CustomDateHandler extends BaseTypeHandler<Date> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String retStr = rs.getString(columnName);
        if(StringUtil.isNotEmpty(retStr) && retStr.length() != 14) {
            String formatterStr = "yyyy-MM-dd HH:mm:ss";
            if(retStr.indexOf("-") < 0) {
                formatterStr = "yyyy/MM/dd HH:mm:ss";
            }
            FastDateFormat formatter = FastDateFormat.getInstance(formatterStr, Locale.getDefault());
            if(formatter != null) {
                try {
                    return formatter.parse(retStr);
                } catch (ParseException e) {
                    throw new ProductException(e);
                }
            }
        }
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
