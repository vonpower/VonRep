/**
 * SchemaDouble.java
 *
 * This file was generated by XMLSpy 2006sp2 Enterprise Edition.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the XMLSpy Documentation for further details.
 * http://www.altova.com/xmlspy
 */


package com.altova.types;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SchemaDouble implements SchemaTypeNumber {
  protected double value;
  protected boolean isempty;
  protected boolean isnull;

  // construction
  public SchemaDouble() {
    setEmpty();
  }

  public SchemaDouble(SchemaDouble newvalue) {
    value = newvalue.value;
    isempty = newvalue.isempty;
    isnull = newvalue.isnull;
  }

  public SchemaDouble(double newvalue) {
    setValue( newvalue );
  }

  public SchemaDouble(String newvalue) {
    parse( newvalue );
  }

  public SchemaDouble(SchemaType newvalue) {
    assign(newvalue);
  }

  public SchemaDouble(SchemaTypeNumber newvalue) {
    assign( (SchemaType)newvalue );
  }

  // setValue, getValue
  public double getValue() {
    return value;
  }

  public void setValue(double newvalue) {
    value = newvalue;
    isempty = false;
    isnull = false;
  }

  public void parse(String newvalue) {
    if( newvalue == null )
      setNull();
    else if( newvalue.length() == 0)
      setEmpty();
    else {
      try {
        value = Double.parseDouble(newvalue);
        isempty = false;
        isnull = false;
      } catch( NumberFormatException e ) {
        throw new StringParseException(e);
      }
    }
  }

  public void assign(SchemaType newvalue) {
    if( newvalue == null || newvalue.isNull() )
      setNull();
    else if( newvalue.isEmpty() )
      setEmpty();
    else if( newvalue instanceof SchemaTypeNumber )
      value = ((SchemaTypeNumber)newvalue).doubleValue();
    else
      throw new TypesIncompatibleException( newvalue, this );
    isempty = false;
  }

  public void setNull() {
    isnull = true;
    isempty = true;
    value = 0;
  }

  public void setEmpty() {
    isnull = false;
    isempty = true;
    value = 0;
  }

  // further
  public int hashCode() {
    return (int) Double.doubleToLongBits(value);
  }

  public boolean equals(Object obj) {
    if (! (obj instanceof SchemaDouble))
      return false;
    return value == ( (SchemaDouble) obj).value;
  }

  public Object clone() {
    return new SchemaDouble(this);
  }

  public String toString() {
    if( isempty || isnull )
      return "";
    String result = Double.toString(value);
    if( result.length() > 2  &&  result.substring( result.length()-2, result.length()).equals(".0") )
      return result.substring( 0, result.length()-2 );
    return result;
  }

  public int length() {
    return toString().length();
  }

  public boolean booleanValue() {
    return !(value==0 || value==Double.NaN);
  }

  public boolean isEmpty() {
    return isempty;
  }

  public boolean isNull() {
    return isnull;
  }

  public int compareTo(Object obj) {
    return compareTo( (SchemaDouble) obj);
  }

  public int compareTo(SchemaDouble obj) {
    return Double.compare(value, obj.value);
  }

  // interface SchemaTypeNumber
  public int numericType() {
    return NUMERIC_VALUE_DOUBLE;
  }

  public int intValue() {
    return (int) value;
  }

  public long longValue() {
    return (long) value;
  }

  public BigInteger bigIntegerValue() {
    try {
      return new BigInteger(toString());
    } catch( NumberFormatException e ) {
      throw new ValuesNotConvertableException( this, new SchemaInteger( 0 ) );
    }
  }

  public float floatValue() {
    return (float) value;
  }

  public double doubleValue() {
    return value;
  }

  public BigDecimal bigDecimalValue() {
    return new BigDecimal(value);
  }
}
