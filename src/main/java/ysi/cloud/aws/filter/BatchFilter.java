package ysi.cloud.aws.filter;

import com.amazonaws.services.ec2.model.Filter;

import java.util.Arrays;
import java.util.List;

public class BatchFilter {

	public static List<Filter> attachTargetFilter() {
		Filter filter = new Filter();
		filter.setName("");
		filter.setValues(Arrays.asList("true"));

		return Arrays.asList(filter);
	}
}