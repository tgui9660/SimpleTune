package simpleTune.romEntity.xmlParse.romXMLData;

public class TableScalingXML {
	private String units;
	private String expression;
	private String to_byte;
	private String format;
	private double fineincrement;
	private double coarseincrement;
	
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getTo_byte() {
		return to_byte;
	}
	public void setTo_byte(String to_byte) {
		this.to_byte = to_byte;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public double getFineincrement() {
		return fineincrement;
	}
	public void setFineincrement(double fineincrement) {
		this.fineincrement = fineincrement;
	}
	public double getCoarseincrement() {
		return coarseincrement;
	}
	public void setCoarseincrement(double coarseincrement) {
		this.coarseincrement = coarseincrement;
	}
	public String toString(){
		String total = "";
		
		total += "units :"+ units +"\n";
		total += "expression :"+ expression +"\n";
		total += "to_byte :"+ to_byte +"\n";
		total += "format :"+ format +"\n";
		total += "fineincrement :"+ fineincrement +"\n";
		total += "coarseincrement :"+ coarseincrement +"\n";
		
		return total;
	}
}
