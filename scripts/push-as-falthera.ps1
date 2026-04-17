<#
Push helper: commit and push current branch to
https://github.com/TwilightSMP/Weapilics as user "Falthera" (aaryadevv8@gmail.com).

Notes:
- This script DOES NOT contain credentials. You must authenticate locally
  (SSH key or GitHub Personal Access Token) when Git prompts.
- To use SSH instead of HTTPS, run with the -UseSSH switch.
- Recommended: run from the repository root in PowerShell.
#>

param(
    [string]$Message = "Fix relic assets and passives",
    [switch]$UseSSH
)

Write-Host "Preparing to push as Falthera <aaryadevv8@gmail.com>"

# Configure repo-local author
git config user.name "Falthera"
git config user.email "aaryadevv8@gmail.com"

$httpRemote = "https://github.com/TwilightSMP/Weapilics.git"
$sshRemote  = "git@github.com:TwilightSMP/Weapilics.git"
$targetRemote = if ($UseSSH.IsPresent) { $sshRemote } else { $httpRemote }

# Ensure remote 'origin' is set to the expected repo
try {
    $originUrl = git remote get-url origin 2>$null
} catch {
    $originUrl = $null
}

if (-not $originUrl) {
    Write-Host "Adding remote 'origin' -> $targetRemote"
    git remote add origin $targetRemote
} elseif ($originUrl -ne $targetRemote) {
    Write-Host "Remote 'origin' exists as $originUrl. Updating to $targetRemote"
    git remote set-url origin $targetRemote
}

# Stage all changes
git add -A

# Commit if there are changes
$changes = git status --porcelain
if (-not $changes) {
    Write-Host "No changes to commit."
} else {
    git commit -m $Message
}

# Push current branch
$branch = git rev-parse --abbrev-ref HEAD
Write-Host "Pushing branch $branch to origin..."
try {
    git push origin $branch
    Write-Host "Push succeeded."
} catch {
    Write-Host "Push failed. You may need to authenticate (PAT or SSH key)."
    Write-Host "If using HTTPS, supply a Personal Access Token as password when prompted."
    Write-Host "If using SSH, ensure your public key is added to your GitHub account."
    exit 1
}

Write-Host "Done."
