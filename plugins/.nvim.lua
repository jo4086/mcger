local util = require("lspconfig.util")
local root = util.root_pattern("settings.gradle", "settings.gradle.kts")(vim.fn.getcwd())

local data_dir = vim.fn.stdpath("cache") .. "/jdtls/workspace-mcger-plugins"

require("lspconfig").jdtls.setup({
	root_dir = function(_)
		return root
	end,
	cmd = {
		"jdtls",
		"-configuration",
		vim.fn.stdpath("cache") .. "/jdtls/config",
		"-data",
		data_dir,
	},
})

-- require("lspconfig").jdtls.setup({
-- 	root_dir = function(_)
-- 		return project_root
-- 	end,
-- })
--
-- vim.api.nvim_create_autocmd("LspAttach", {
-- 	callback = function(args)
-- 		local client = vim.lsp.get_client_by_id(args.data.client_id)
-- 		if client and client.name == "jdtls" and client.config.root_dir ~= project_root then
-- 			client.stop()
-- 		end
-- 	end,
-- })
