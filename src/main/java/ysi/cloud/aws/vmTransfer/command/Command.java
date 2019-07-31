package ysi.cloud.aws.vmTransfer.command;

public class Command {
    /* 파티셔닝 명령어 */
    public static final String partitioning = "echo -e \"o\\nn\\np\\n1\\n\\n\\nw\" | sudo fdisk %s";
    /* ext4 포맷 */
    public static final String ext4Format = "mkfs.ext4 %s";
    /* mount */
    public static final String mount = "mount %s %s";
    /* 이미지 생성 */
    public static final String imageCreate = "sudo dd if=%s of=%s &";
}