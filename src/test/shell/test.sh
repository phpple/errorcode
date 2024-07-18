#! /bin/bash

result=$(curl http://localhost:21200/foo/detail/0 -s|jq .code)
echo -e "expect:110000001\nactual:$result\n"
# 110000001

result=$(curl http://localhost:21200/foo/detail/101 -s|jq .code)
echo -e "expect:200\nactual:$result\n"
# 200

result=$(curl http://localhost:21200/foo/detail/101 -s|jq .data.id)
echo -e "expect:101\nactual:$result\n"
# 101

result=$(curl http://localhost:21200/foo/detail/99 -s|jq .code)
echo -e "expect:500\nactual:$result\n"
# 500

result=$(curl http://localhost:21200/foo/detail/99 -s|jq .rawCode)
echo -e "expect:210000001\nactual:$result\n"
# 210000001