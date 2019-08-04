package ysi.cloud.aws.sns.s3.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-15
 * Time: 오후 3:40
 */
@Controller
public class SnsS3EventController {

    /*
        {
            "Type": "Notification",
                "MessageId": "5e5eb07a-0085-5713-b5ee-e5baf5c10418",
                "TopicArn": "arn:aws:sns:ap-northeast-2:782667473635:exportInstance",
                "Message": "{\"Records\":[{\"eventVersion\":\"2.1\",\"eventSource\":\"aws:s3\",\"awsRegion\":\"ap-northeast-2\",\"eventTime\":\"2019-08-04T15:00:20.432Z\",\"eventName\":\"ObjectCreated:CompleteMultipartUpload\",\"userIdentity\":{\"principalId\":\"A1PYY5E91BMUGC\"},\"requestParameters\":{\"sourceIPAddress\":\"125.133.218.169\"},\"responseElements\":{\"x-amz-request-id\":\"49B7F9D5844AB3F3\",\"x-amz-id-2\":\"N7bPzIn2f0km/9t5thUq2nbRBVs+pPydZ6V+Vb7flXXWZf60AUM8lOGu0ZSIl05AgTq0rX4tK4g=\"},\"s3\":{\"s3SchemaVersion\":\"1.0\",\"configurationId\":\"test\",\"bucket\":{\"name\":\"kyoyaiida1\",\"ownerIdentity\":{\"principalId\":\"A1PYY5E91BMUGC\"},\"arn\":\"arn:aws:s3:::kyoyaiida1\"},\"object\":{\"key\":\"Git-2.22.0-64-bit.exe\",\"size\":47087448,\"eTag\":\"1e39c5cf0e6446a11cbd4e90a5f85873-3\",\"sequencer\":\"005D46F2FF3125085B\"}}}]}",
                "Subject": "Amazon S3 Notification",
                "Timestamp": "2019-08-04T15:00:20.755Z",
                "SignatureVersion": "1",
                "Signature": "mO8GZthPrrEjm7CZP5+uu/PlewglhHJYuYU5r2NKlWkQOoA4odakLZXJnd1Ndgoji7+2bceYrFP0BkKx8yWaRPyWvEbUizOgpFWg2mxd2sYQkTNHk5NHqvWDj1BGSV1dsDd8L0eMNXzfudoiL3LUJqW+KFIKsgQbRiDDA8ui3ngC6g7mZL5OQLRfxsu0B41cuGSvVtmP1ckWwXXzCVnlEZ+evahW6TZOl1Z22Tj/q/XlAsL7KeztTTGpHRIHwCIXtaX2ZMz3sGHdh953JdYL6LSbXOfqeYEeIJPImiEVsBOeMlX+tzNKpEBIcUVc0DFylNU7SeN1wuAzPjMxgLKBaw==",
                "SigningCertURL": "https://sns.ap-northeast-2.amazonaws.com/SimpleNotificationService-6aad65c2f9911b05cd53efda11f913f9.pem",
                "UnsubscribeURL": "https://sns.ap-northeast-2.amazonaws.com/?Action=Unsubscribe&SubscriptionArn=arn:aws:sns:ap-northeast-2:782667473635:exportInstance:35f7c67d-30c3-4464-86f3-33d884ed3fd4"
        }
        {"Records":[{"eventVersion":"2.1","eventSource":"aws:s3","awsRegion":"ap-northeast-2","eventTime":"2019-08-04T15:08:54.978Z","eventName":"ObjectCreated:CompleteMultipartUpload","userIdentity":{"principalId":"A1PYY5E91BMUGC"},"requestParameters":{"sourceIPAddress":"125.133.218.169"},"responseElements":{"x-amz-request-id":"D62FF3D027CDDF39","x-amz-id-2":"VXDeFTRbCEr+EnmvZYJskQKa0lBrQRlOojZ+kQpJ3R2i+o7CgzZdnThkr36TQNiHGvF4/ihdy4M="},"s3":{"s3SchemaVersion":"1.0","configurationId":"test","bucket":{"name":"kyoyaiida1","ownerIdentity":{"principalId":"A1PYY5E91BMUGC"},"arn":"arn:aws:s3:::kyoyaiida1"},"object":{"key":"Git-2.22.0-64-bit.exe","size":47087448,"eTag":"1e39c5cf0e6446a11cbd4e90a5f85873-3","sequencer":"005D46F5031200ACEC"}}}]}
    */
    @RequestMapping(value = "/aws/ec2/volume/test", method = RequestMethod.POST)
    public @ResponseBody String test(@RequestBody String str) {
        System.out.println("str : " + str);
        return "test";
    }

}