import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.SQLProjection;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

public class CoalesceSQLProjection extends SQLProjection
{
    private final String firstValue;
    private final String secondValue;
    private static final Type[] types = new Type[] {StringType.INSTANCE};

    /**
     * Constructor
     * @param columnAlias
     *            alias of created column
     * @param firstValue
     *            first value to check is null
     * @param secondValue
     *            second value to check is null
     */
    public CoalesceSQLProjection(String columnAlias, String firstValue, String secondValue)
    {
        super(getSQL(columnAlias, firstValue, secondValue), new String[] {columnAlias}, types);
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    private static String getSQL(String columnAlias, String firstValue, String secondValue)
    {
        return "COALESCE(" + firstValue + ", " + secondValue + " ) as " + columnAlias;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toSqlString(Criteria criteria, int loc, CriteriaQuery criteriaQuery)
    {
        String sql = super.toSqlString(criteria, loc, criteriaQuery);
        sql = sql.replace(firstValue, criteriaQuery.getColumn(criteria, firstValue));
        sql = sql.replace(secondValue, criteriaQuery.getColumn(criteria, secondValue));
        return sql;
    }
}
