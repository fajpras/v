#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/../.."

env_get() { grep -E "^$1=" .env | head -1 | cut -d= -f2- | sed -e 's/^["'\'']//' -e 's/["'\'']$//' || true; }

# escape untuk string SQL ('...')
esc() { local v="${1//\\/\\\\}"; printf '%s' "${v//\'/\\\'}"; }

DB_HOST="$(env_get DB_HOST)"; DB_HOST="${DB_HOST:-127.0.0.1}"
DB_PORT="$(env_get DB_PORT)"; DB_PORT="${DB_PORT:-3306}"
DB="$(env_get DB_DATABASE)"
CH="$(env_get DB_CLIENT_HOST)"; CH="${CH:-localhost}"
ROOT="$(env_get MYSQL_ROOT_USER)"; ROOT="${ROOT:-root}"

for k in APP USER ADMIN DBA; do
  u="$(env_get DB_${k}_USERNAME)"; p="$(env_get DB_${k}_PASSWORD)"
  [[ -z "$u" || -z "$p" ]] && { echo "DB_${k}_USERNAME / DB_${k}_PASSWORD belum diisi di .env" >&2; exit 1; }
  printf -v "U_$k" '%s' "$(esc "$u")"
  printf -v "P_$k" '%s' "$(esc "$p")"
done
[[ -z "$DB" ]] && { echo "DB_DATABASE belum diisi" >&2; exit 1; }

mysql -h "$DB_HOST" -P "$DB_PORT" -u "$ROOT" -p <<SQL
CREATE DATABASE IF NOT EXISTS \`$DB\` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE ROLE IF NOT EXISTS 'admin_role', 'user_role';

CREATE USER IF NOT EXISTS '$U_DBA'@'$CH'   IDENTIFIED BY '$P_DBA';
CREATE USER IF NOT EXISTS '$U_APP'@'$CH'   IDENTIFIED BY '$P_APP';
CREATE USER IF NOT EXISTS '$U_USER'@'$CH'  IDENTIFIED BY '$P_USER';
CREATE USER IF NOT EXISTS '$U_ADMIN'@'$CH' IDENTIFIED BY '$P_ADMIN';
ALTER USER '$U_DBA'@'$CH'   IDENTIFIED BY '$P_DBA';
ALTER USER '$U_APP'@'$CH'   IDENTIFIED BY '$P_APP';
ALTER USER '$U_USER'@'$CH'  IDENTIFIED BY '$P_USER';
ALTER USER '$U_ADMIN'@'$CH' IDENTIFIED BY '$P_ADMIN';

GRANT ALL PRIVILEGES ON \`$DB\`.* TO '$U_DBA'@'$CH' WITH GRANT OPTION;

-- sesuaikan hak akses dengan kebutuhanmu
GRANT SELECT, INSERT, UPDATE ON \`$DB\`.* TO 'user_role';
GRANT SELECT, INSERT, UPDATE, DELETE ON \`$DB\`.* TO 'admin_role';
GRANT SELECT, INSERT, UPDATE, DELETE ON \`$DB\`.* TO '$U_APP'@'$CH';

GRANT 'user_role'  TO '$U_USER'@'$CH';
GRANT 'admin_role' TO '$U_ADMIN'@'$CH';
SET DEFAULT ROLE 'user_role'  TO '$U_USER'@'$CH';
SET DEFAULT ROLE 'admin_role' TO '$U_ADMIN'@'$CH';
FLUSH PRIVILEGES;
SQL

echo "Selesai. Lanjut: php artisan migrate --database=mysql_dba"