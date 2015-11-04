package mca.enums;

import mca.ai.AIMood;
import mca.data.PlayerMemory;
import mca.entity.EntityHuman;

public enum EnumInteraction 
{
	INTERACT(1, "interact"),
	FOLLOW(2, "follow"),
	STAY(3, "stay"),
	MOVE(4, "move"),
	TRADE(5, "trade"),
	SET_HOME(6, "sethome"),
	RIDE_HORSE(7, "ridehorse"),
	SPECIAL(8, "special"),
	PROCREATE(9, "procreate"),
	PICK_UP(10, "pickup"),
	TAKE_GIFT(11, "takegift"),
	WORK(12, "work"),
	ADMIN(13, "admin"),
	INVENTORY(14, "inventory"),
	
	CHAT(101, "chat", 1, 60, 5),
	JOKE(102, "joke", 1, 60, 5),
	GIFT(103, "gift"),
	SHAKE_HAND(104, "handshake", 1, 80, 3),
	TELL_STORY(105, "tellstory", 1, 50, 7),
	FLIRT(106, "flirt", 2, 50, 8),
	HUG(107, "hug", 3, 30, 9),
	KISS(108, "kiss", 3, 15, 10),
	
	FARMING(201, "farming"),
	FARMING_MODE(202, "farmingmode"),
	FARMING_TARGET(203, "farmingtarget"),
	FARMING_RADIUS(204, "farmingradius"),
	
//	FISHING(202, "fishing"),
	
	HUNTING(301, "hunting"),
	HUNTING_MODE(302, "huntingmode"),
	
	WOODCUTTING(401, "woodcutting"),
	WOODCUTTING_TREE(402, "woodcuttingtree"),
	WOODCUTTING_REPLANT(403, "woodcuttingreplant"),
	
	MINING(501, "mining"),
	MINING_MODE(502, "miningmode"),
	MINING_TARGET(503, "miningtarget"),
	
	COOKING(601, "cooking"),
	
	COMBAT(701, "combat"),
	ATTACK_ANIMALS(702, "attackanimals"),
	ATTACK_MOBS(703, "attackmobs"),
        
        // build menu
        BUILD(4201, "build"),
        BUILDNEXTPAGE(4202, "buildnextpage"),
        BUILDNORTH(4203, "north"),
        BUILDEAST(4204, "east"),
        BUILDSOUTH(4205, "south"),
        BUILDWEST(4206, "west"),
        BUILDPAGE0(4210, "buildpage0"),
        
        // end build menu space at 4299 or something
	
	HIRE(801, "hire"),
	LENGTH(802, "length"),
	ACCEPT(803, "accept"),
	
	START(997, "start"),
	STOP(998, "stop"),
	BACK(999, "back"),
	
	ASKTOMARRY(1000, "asktomarry"),
	DIVORCE(1001, "divorce"),
	HAVEBABY(1002, "havebaby"),
	ASKTOMARRY_ACCEPT(1003, "asktomarryaccept"),
	HAVEBABY_ACCEPT(1004, "havebabyaccept"), 
	
	RESETBABY(1101, "resetbaby"),
	ADOPTBABY(1102, "adoptbaby");
	
	private int id;
	private String name;
	private int relationshipLevel;
	private int basePoints;
	private int baseChance;

	EnumInteraction(int id, String name)
	{
		this.id = id;
		this.name = name;
		this.relationshipLevel = 0;
		this.baseChance = 0;
		this.basePoints = 0;
	}
	
	EnumInteraction(int id, String name, int relationshipLevel, int baseChance, int basePoints)
	{
		this(id, name);
		this.relationshipLevel = relationshipLevel;
		this.baseChance = baseChance;
		this.basePoints = basePoints;
	}
	
	public static EnumInteraction fromId(int id)
	{
		for (EnumInteraction interaction : EnumInteraction.values())
		{
			if (interaction.id == id)
			{
				return interaction;
			}
		}
		
		return null;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getBasePoints()
	{
		return basePoints;
	}
	
	public int getBaseChance()
	{
		return baseChance;
	}

	public int getRelationshipLevel()
	{
		return relationshipLevel;
	}
	
	public int getBonusChanceForCurrentPoints(int hearts) 
	{
		int returnAmount = 0;

		switch (this)
		{
		case FLIRT: returnAmount = hearts >= 60 ? 5 : hearts >= 70 ? 10 : hearts >= 80 ? 15 : hearts >= 90 ? 20 : hearts >= 100 ? 25 : 0; break;
		case HUG: returnAmount = hearts >= 60 ? 10 : hearts >= 70 ? 15 : hearts >= 80 ? 20 : hearts >= 90 ? 25 : hearts >= 100 ? 30 : 0; break;
		case KISS: returnAmount = hearts >= 60 ? 20 : hearts >= 70 ? 30 : hearts >= 80 ? 40 : hearts >= 90 ? 50 : hearts >= 100 ? 60 : 0; break;
		}
		
		return returnAmount;
	}
	
	public int getSuccessChance(EntityHuman villager, PlayerMemory memory)
	{
		return getBaseChance() - memory.getInteractionFatigue() * 6
				+ villager.getPersonality().getSuccessModifierForInteraction(this) 
				+ villager.getAI(AIMood.class).getMood(villager.getPersonality()).getSuccessModifierForInteraction(this)
				+ getBonusChanceForCurrentPoints(memory.getHearts());
	}
}
