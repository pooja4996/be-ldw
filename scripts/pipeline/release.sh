bucket_name="ldw-traffic-count-code"
aws_key="folder1/ldwrest.zip"
aws_access_key="AKIATCEFXSLUAXMNB7P3"
aws_access_secret="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"
local_path="ldwrest.zip"

# Remove any existing versions of a ZIP
rm -rf $local_path

# Create a zip of the current directory.
zip -r $local_path . -x ".git/" ".git/**" ".git*/*" "scripts/" "scripts/**" .DS_Store

# Install required dependencies for Python script.
pip3 install boto3

# Run upload script
python3 scripts/pipeline/upload_file_to_s3.py $bucket_name $aws_key $aws_access_key $aws_access_secret $local_path
