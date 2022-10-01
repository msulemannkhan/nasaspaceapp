import boto3


SE_BUCKET_NAME = ""

s3_client = boto3.client('s3')
with open("hello.txt","a+") as f:
    f.write('Hello, world suleman!')

# Upload the file to S3
s3_client.upload_file('hello.txt', SE_BUCKET_NAME, 'hello_remote.txt')

# Download the file from S3
s3_client.download_file(SE_BUCKET_NAME, 'hello_remote.txt', 'hello2.txt')
print(open('hello2.txt').read())